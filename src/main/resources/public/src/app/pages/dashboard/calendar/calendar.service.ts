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
 *
 */
import {Injectable} from "@angular/core";
import {BaThemeConfigProvider} from "../../../theme";
import {Http} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {CalendarEvent} from "../../model/calendar/calendar";
import * as AppConst from "./../../../core/app.const";
import {SerializationHelper} from "../../../helper/serialization-helper";
import {environment} from "../../../../environments/environment";

@Injectable()
export class CalendarService {

  private _events: CalendarEvent[] = [];

  constructor(private _baConfig: BaThemeConfigProvider, private _http: Http) {
  }

  public getCalendarEvents(): Observable<CalendarEvent[]> {
    return this._http.get(AppConst.BACKEND_API_ROOT_URL + '/calendars')
      .map(res => res.json())
      .map(responses => {
        this._events = [];
        responses.forEach(jsonEvent => this._events.push(SerializationHelper.toInstanceFromJsonObj(new CalendarEvent(), jsonEvent)));
        return this._events;
      });
  }

  public post(event: CalendarEvent): Observable<CalendarEvent> {
    return this._http.post(AppConst.BACKEND_API_ROOT_URL + '/calendars', JSON.stringify(event), environment.PROPERTIES["HOST_OPTIONS"])
      .map(resp => SerializationHelper.toInstanceFromJsonObj(new CalendarEvent(), resp.json()));
  }

  public put(event: CalendarEvent): Observable<CalendarEvent> {
    return this._http.put(AppConst.BACKEND_API_ROOT_URL + '/calendars', JSON.stringify(event), environment.PROPERTIES["HOST_OPTIONS"])
      .map(resp => SerializationHelper.toInstanceFromJsonObj(new CalendarEvent(), resp.json()));
  }
}
