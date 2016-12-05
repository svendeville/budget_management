import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import {Observable} from "rxjs/Observable";
import { User } from "./../model/user";
import { SerializationHelper } from "./../../helper/serialization-helper";
import { ENV_PROPERTIES } from './../../environment';

@Injectable()
export class RegisterService {

  constructor(private _http: Http) { }

   public post(user:User): Observable<User> {
      let host:string = ENV_PROPERTIES["HOST_SERVICES"];
      return this._http.post(host + "/users", JSON.stringify(user), ENV_PROPERTIES["HOST_OPTIONS"])
      .map(resp => SerializationHelper.toInstanceFromJsonObj(new User(), resp.json()));
   }
}
