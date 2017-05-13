import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgaModule} from "../../theme/nga.module";
import {BaThemeConfigProvider} from "../../theme";
import {Dashboard} from "./dashboard.component";
import {routing} from "./dashboard.routing";
import {TodoComponent, TodoService} from "./todo";
import {Calendar} from "./calendar";
import {CalendarService} from "./calendar/calendar.service";
import {NgbModalModule} from "@ng-bootstrap/ng-bootstrap";
import {AppTranslationModule} from "../../app.translation.module";
import {CalendarFormModalComponent} from "./calendar/component/calendar-form-modal.component";

@NgModule({
  imports: [
    CommonModule,
    AppTranslationModule,
    ReactiveFormsModule,
    FormsModule,
    NgaModule,
    NgbModalModule,
    routing
  ],
  declarations: [
    TodoComponent,
    Calendar,
    Dashboard,
    CalendarFormModalComponent
  ],
  entryComponents: [
    CalendarFormModalComponent
  ],
  providers: [
    CalendarService,
    TodoService,
    BaThemeConfigProvider
  ]
})
export class DashboardModule {
}
