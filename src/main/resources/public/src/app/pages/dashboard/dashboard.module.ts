import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {NgaModule} from "../../theme/nga.module";
import {BaThemeConfigProvider} from "../../theme";
import {Dashboard} from "./dashboard.component";
import {routing} from "./dashboard.routing";
import {TodoComponent, TodoService} from "./todo";
import {Calendar} from "./calendar";
import {CalendarService} from "./calendar/calendar.service";
import {Modals} from "../ui/components/modals/modals.component";
import {NgbModalModule} from "@ng-bootstrap/ng-bootstrap";
import {AppTranslationModule} from "../../app.translation.module";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AppTranslationModule,
    NgaModule,
    NgbModalModule,
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
export class DashboardModule {
}
