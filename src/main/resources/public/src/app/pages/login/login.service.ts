/*
 * @Copyright 2016 Sylvain Vendeville.
 * This file is part of Budget Managment.
 * Budget Managment is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Budget Managment is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MesComptes. If not, see <http://www.gnu.org/licenses/>.
 */
import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {Credential, User, UserEventsService} from "./../model/user";
import * as AppConst from "./../../core/app.const";
import {SecurityToken} from "./../../core/securityToken";
import {environment} from "../../../environments/environment";

@Injectable()
export class LoginService {

  constructor(private http: Http, private userEventsService: UserEventsService, private router: Router) {
  }

  public post(credential: Credential): Observable<User> {
    return this.http.post(AppConst.BACKEND_API_ROOT_URL + "/users/login", JSON.stringify(credential), environment.PROPERTIES["HOST_OPTIONS"])
      .map(res => {
        let user: User = new User(res.json());
        let securityToken: SecurityToken = new SecurityToken(
          {
            publicSecret: res.headers.get(AppConst.HEADER_X_SECRET) ? res.headers.get(AppConst.HEADER_X_SECRET) : user.publicSecret,
            securityLevel: 'HmacSHA256'
          }
        );

        localStorage.setItem(AppConst.CSRF_CLAIM_HEADER, res.headers.get(AppConst.CSRF_CLAIM_HEADER) ? res.headers.get(AppConst.CSRF_CLAIM_HEADER) : user.csrfId);
        localStorage.setItem(AppConst.STORAGE_ACCOUNT_TOKEN, res.text());
        localStorage.setItem(AppConst.STORAGE_SECURITY_TOKEN, JSON.stringify(securityToken));
        localStorage.setItem(AppConst.STORAGE_SECURITY_JWT, res.headers.get(AppConst.STORAGE_SECURITY_JWT) ? res.headers.get(AppConst.STORAGE_SECURITY_JWT) : user.jwt);

        this.sendLoginSuccess(user);
        return user;
      });
  }

  private sendLoginSuccess(user?: User): void {
    if (!user) {
      user = new User(JSON.parse(localStorage.getItem(AppConst.STORAGE_ACCOUNT_TOKEN)));
    }
    this.userEventsService.loginSuccess(user);
  }

  public isAuthenticated(): boolean {
    return !!localStorage.getItem(AppConst.STORAGE_ACCOUNT_TOKEN);
  }

  private removeAccount(): void {
    localStorage.removeItem(AppConst.STORAGE_ACCOUNT_TOKEN);
    localStorage.removeItem(AppConst.STORAGE_SECURITY_TOKEN);
    localStorage.removeItem(AppConst.CSRF_CLAIM_HEADER);
  }

  public logout(callServer: boolean = true): void {
    console.log('Logging out');

    if (callServer) {
      this.http.get(AppConst.BACKEND_API_ROOT_URL + '/logout').subscribe(() => {
        this.userEventsService.logout(new User(JSON.parse(localStorage.getItem(AppConst.STORAGE_ACCOUNT_TOKEN))));
        this.removeAccount();
        this.router.navigate(['/login']);
      });
    } else {
      this.removeAccount();
      this.router.navigate(['/login']);
    }
  }

  public isAuthorized(roles: Array<string>): boolean {
    let authorized: boolean = false;
    if (this.isAuthenticated() && roles) {
      let user: User = new User(JSON.parse(localStorage.getItem(AppConst.STORAGE_ACCOUNT_TOKEN)));
      if (user && user.authorities) {

        roles.forEach((role: string) => {
          if (user.authorities.indexOf(role) !== -1) {
            authorized = true;
          }
        });
      }
    }
    return authorized;
  }
}
