import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {NgaModule} from "../../theme/nga.module";
import {BaThemeConfigProvider} from "../../theme";
import {Dashboard} from "./dashboard.component";
import {routing} from "./dashboard.routing";
import {TodoService, TodoComponent} from "./todo";
import {Calendar} from "./calendar";
import {CalendarService} from "./calendar/calendar.service";
import {Modals} from "../ui/components/modals/modals.component";
import {ModalModule} from "ng2-bootstrap";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgaModule,
    ModalModule,
    routing
  ],
  declarations: [
    TodoComponent,
    Calendar,
    Modals,
    Dashboard
  ],
  providers: [
    CalendarService,
    TodoService,
    BaThemeConfigProvider
  ]
})
export default class DashboardModule {}
