import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppTranslationModule} from "../../app.translation.module";
import {NgaModule} from "../../theme/nga.module";
import {Login} from "./login.component";
import {routing} from "./login.routing";
import {LoginService} from "./login.service";
import {UserEventsService} from "../model/user/user.events.service";


@NgModule({
  imports: [
    CommonModule,
    AppTranslationModule,
    ReactiveFormsModule,
    FormsModule,
    NgaModule,
    routing
  ],
  declarations: [
    Login
  ],
  providers: [
    LoginService,
    UserEventsService
  ]
})
export class LoginModule {
}
