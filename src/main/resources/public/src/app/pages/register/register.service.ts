import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import {Observable} from "rxjs/Observable";
import { User } from "./../model/user";
import { SerializationHelper } from "./../../helper/serialization-helper";

@Injectable()
export class RegisterService {

  constructor(private _http: Http) { }

   public post(user:User): Observable<User> {
      return this._http.post("/users", JSON.stringify(user))
      .map(resp => SerializationHelper.toInstanceFromJsonObj(new User(), resp.json()));
   }
}
