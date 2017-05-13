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
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {GlobalState} from "../../../../global.state";
import {CalendarEvent} from "../../../model/calendar/calendar";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BaThemeConfigProvider} from "../../../../theme/theme.configProvider";
@Component({
  selector: 'calendar-form-modal',
  templateUrl: 'calendar-form-modal.html'
})
export class CalendarFormModalComponent {


  public form: FormGroup;
  public calendarTitle: AbstractControl;
  public calendarStart: AbstractControl;
  public calendarEnd: AbstractControl;
  public calendarColor: AbstractControl;

  public start: string;
  public end: string;
  public colors: any[] = [];
  public modalHeader: string;

  constructor(private activeModal: NgbActiveModal, private _state: GlobalState, fb: FormBuilder, private _baConfig: BaThemeConfigProvider) {
    this.form = fb.group({
      'calendarTitle': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
      'calendarStart': ['', Validators.compose([Validators.required])],
      'calendarEnd': ['', Validators.compose([Validators.required])],
      'calendarColor': ['']
    });

    this.calendarTitle = this.form.controls['calendarTitle'];
    this.calendarStart = this.form.controls['calendarStart'];
    this.calendarEnd = this.form.controls['calendarEnd'];
    this.calendarColor = this.form.controls['calendarColor'];

    this.colors.push({name: 'blueStone', value: this._baConfig.get().colors.dashboard.blueStone});
    this.colors.push({name: 'surfieGreen', value: this._baConfig.get().colors.dashboard.surfieGreen});
    this.colors.push({name: 'silverTree', value: this._baConfig.get().colors.dashboard.silverTree});
    this.colors.push({name: 'gossip', value: this._baConfig.get().colors.dashboard.gossip});
    this.colors.push({name: 'white', value: this._baConfig.get().colors.dashboard.white});
  }

  ngOnInit() {
    this.calendarStart.setValue(this.start);
    this.calendarEnd.setValue(this.end);
  }

  closeModal() {
    this.activeModal.close();
  }

  private _addCalendarEvent(isCanceled: boolean): void {
    this.closeModal();
    if (isCanceled) {
      this._state.notifyDataChanged('addCalendarEvent', null);
      return;
    }
    let calendar: CalendarEvent = new CalendarEvent();
    calendar.title = this.calendarTitle.value;
    calendar.start = this.calendarStart.value;
    calendar.end = this.calendarEnd.value;
    calendar.color = this.calendarColor.value;
    this._state.notifyDataChanged('addCalendarEvent', calendar);
  }
}
