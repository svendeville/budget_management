import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Http, HttpModule, RequestOptions, XHRBackend} from "@angular/http";
import {RouterModule} from "@angular/router";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
/*
 * Platform and Environment providers/directives/pipes
 */
import {routing} from "./app.routing";
// App is our top level component
import {App} from "./app.component";
import {AppState, InternalStateType} from "./app.service";
import {GlobalState} from "./global.state";
import {NgaModule} from "./theme/nga.module";
import {PagesModule} from "./pages/pages.module";
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import {UserEventsService} from "./pages/model/user/user.events.service";
import {HmacHttpClient} from "./core/hmac-http-client";
import {ContextMenuModule, ContextMenuService} from "angular2-contextmenu";


// Application wide providers
const APP_PROVIDERS = [
  AppState,
  GlobalState
];

export type StoreType = {
  state: InternalStateType,
  restoreInputValues: () => void,
  disposeOldHosts: () => void
};

export function httpFactory(xhrBackend: XHRBackend, requestOptions: RequestOptions, accountEventService: UserEventsService) {
  return new HmacHttpClient(xhrBackend, requestOptions, accountEventService);
}

/**
 * `AppModule` is the main entry point into Angular2's bootstraping process
 */
@NgModule({
  bootstrap: [App],
  declarations: [
    App
  ],
  imports: [ // import Angular's modules
    BrowserModule,
    HttpModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    NgaModule.forRoot(),
    NgbModule.forRoot(),
    PagesModule,
    routing,
    ContextMenuModule
  ],
  providers: [ // expose our Services and Providers into Angular's dependency injection
    APP_PROVIDERS,
    UserEventsService, ContextMenuService,
    {provide: LocationStrategy, useClass: HashLocationStrategy},
    {
      provide: Http,
      useFactory: httpFactory,
      deps: [XHRBackend, RequestOptions, UserEventsService],
      multi: false
    }
  ]
})

export class AppModule {

  constructor(public appState: AppState) {
  }
}
