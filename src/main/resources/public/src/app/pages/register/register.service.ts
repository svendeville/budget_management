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
import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import {Observable} from "rxjs/Observable";
import { User } from "./../model/user/user";
import { SerializationHelper } from "./../../helper/serialization-helper";
import * as AppConst from './../../core/app.const';
import { ENV_PROPERTIES } from './../../environment';

@Injectable()
export class RegisterService {

  constructor(private _http: Http) { }

   public post(user:User): Observable<User> {
      return this._http.post(AppConst.BACKEND_API_ROOT_URL + "/users/create", JSON.stringify(user), ENV_PROPERTIES["HOST_OPTIONS"])
      .map(resp => SerializationHelper.toInstanceFromJsonObj(new User(), resp.json()));
   }
}
