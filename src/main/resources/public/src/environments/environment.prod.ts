import {Headers, RequestOptionsArgs} from "@angular/http";
let reqOptions: RequestOptionsArgs = {};
reqOptions.headers = new Headers();
reqOptions.headers.set("Content-Type", "application/json;charset=UTF-8");
export const environment = {
  production: true,
  PROPERTIES: {
    HOST_SERVICES: "http://localhost:8080/api",
    HOST_OPTIONS: reqOptions
  }
};
