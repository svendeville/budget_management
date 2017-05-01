// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
import {Headers, RequestOptionsArgs} from "@angular/http";
let reqOptions: RequestOptionsArgs = {};
reqOptions.headers = new Headers();
reqOptions.headers.set("Content-Type", "application/json;charset=UTF-8");
export const environment = {
  production: false,
  PROPERTIES: {
    HOST_SERVICES: "http://localhost:8080/api",
    HOST_OPTIONS: reqOptions
  }
};
