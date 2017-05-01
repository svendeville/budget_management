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
import {Component, ElementRef, ViewChild} from "@angular/core";
import {CalendarService} from "./calendar.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'calendar',
  styleUrls: ['./calendar.scss'],
  templateUrl: './calendar.html'
})
export class Calendar {

  @ViewChild('calendarForm')
  calendarForm: ElementRef;

  public calendarConfiguration:any;
  private _calendar:Object;

  constructor(private _calendarService: CalendarService, private modalService: NgbModal) {
    this.calendarConfiguration = this._calendarService.getData();
    this.calendarConfiguration.select = (start, end) => this._onSelect(start, end);
  }

  public onCalendarReady(calendar):void {
    this._calendar = calendar;
  }

  private _onSelect(start, end):void {
    if (this._calendar != null) {
      this.modalService.open(this.calendarForm, {})
      /*
      let title = prompt('Event Title:');
      let eventData:any;
      if (title) {
        eventData = {
          title: title,
          start: start,
          end: end
        };
        jQuery(this._calendar).fullCalendar('renderEvent', eventData, true);
      }
       jQuery(this._calendar).fullCalendar('unselect');*/
    }
  }
}
