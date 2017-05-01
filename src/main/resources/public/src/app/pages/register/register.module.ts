import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgaModule} from "../../theme/nga.module";

import {Register} from "./register.component";
import {routing} from "./register.routing";
import {RegisterService} from "./register.service";
import {AppTranslationModule} from "../../app.translation.module";


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
    Register
  ],
  providers: [
    RegisterService
  ]
})
export class RegisterModule {
}
