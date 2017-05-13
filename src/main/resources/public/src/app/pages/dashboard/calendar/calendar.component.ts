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
import {Component} from "@angular/core";
import {CalendarService} from "./calendar.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CalendarFormModalComponent} from "./component/calendar-form-modal.component";
import {GlobalState} from "../../../global.state";
import {CalendarEvent} from "../../model/calendar/calendar";
import * as jQuery from "jquery";
import {ContextMenuService} from "angular2-contextmenu/src/contextMenu.service";

@Component({
  selector: 'calendar',
  styleUrls: ['./calendar.scss'],
  templateUrl: './calendar.html'
})
export class Calendar {

  public calendarConfiguration: any = {
    header: {
      left: 'prev,next today',
      center: 'title',
      right: 'month,agendaWeek,agendaDay'
    },
    defaultDate: Date.now(),
    selectable: true,
    selectHelper: true,
    editable: true,
    eventLimit: true
  };
  private _calendar: Object;

  public menuOptions = [
    {
      html: () => 'Modifier',
      click: (item, $event) => {
        this._onModify(item);
      },
    },
    {
      html: (): string => 'Supprimer',
      click: (item, $event): void => {
        this._onDelete(item);
      }
    }
  ];

  constructor(private _calendarService: CalendarService, private contextMenuService: ContextMenuService,
              private modalService: NgbModal, private _state: GlobalState) {
    this._calendarService.getCalendarEvents()
      .subscribe(events => {
        if (events.length > 0) {
          jQuery(this._calendar).fullCalendar('renderEvents', events, true);
        }
      });
    this.calendarConfiguration.select = (start, end) => this._onSelect(start, end);
    this.calendarConfiguration.eventClick = (event) => this.onContextMenu(event);
    let that = this;
    this._state.subscribe('addCalendarEvent', (calendarEvent) => that._onAddEvent(calendarEvent));
  }

  public onCalendarReady(calendar): void {
    this._calendar = calendar;
  }

  public onContextMenu(event): boolean {
    let eventCalendar: CalendarEvent = new CalendarEvent();
    eventCalendar.title = event.title;
    eventCalendar.start = event.start;
    eventCalendar.end = event.end;
    eventCalendar.color = event.color;
    this.contextMenuService.show.next({
      actions: this.menuOptions,
      event: event,
      item: eventCalendar,
    });
    return false;
  }

  private _onAddEvent(calendarEvent: CalendarEvent) {
    if (calendarEvent) {
      this._calendarService.post(calendarEvent)
        .subscribe(event => {
          jQuery(this._calendar).fullCalendar('renderEvent', event, true);
          jQuery(this._calendar).fullCalendar('unselect');
        });
    }
  }

  private _onSelect(start, end): void {
    if (this._calendar != null) {
      let modalCalendarForm = this.modalService.open(CalendarFormModalComponent, {
        size: 'lg',
        backdrop: 'static'
      });
      modalCalendarForm.componentInstance.start = start;
      modalCalendarForm.componentInstance.end = end;
    }
  }

  private _onModify(event: CalendarEvent) {
    if (this._calendar != null) {
      let modalCalendarForm = this.modalService.open(CalendarFormModalComponent, {
        size: 'lg',
        backdrop: 'static'
      });
      modalCalendarForm.componentInstance.title = event.title;
      modalCalendarForm.componentInstance.start = event.start;
      modalCalendarForm.componentInstance.end = event.end;
      modalCalendarForm.componentInstance.color = event.color;
    }
  }

  private _onDelete(item) {
    alert(item);
  }
}
