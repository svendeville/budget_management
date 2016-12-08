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
 */
import {Injectable} from '@angular/core';
import {Http,Response,RequestOptionsArgs, Headers, RequestOptions, ConnectionBackend} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {SecurityToken} from './securityToken';
import * as AppConst from './app.const';
import {UserEventsService} from '../pages/model/user/user.events.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/share';
import {Observer} from 'rxjs/Observer';
import {CryptoJS} from '../../../../../../../typings/cryptojs/cryptojs.d.ts';

///<reference path="../../../../../../../typings/cryptojs/cryptojs.d.ts" />

@Injectable()
export class HmacHttpClient extends Http {
    http:Http;
    userEventsService:UserEventsService;
    constructor(_backend: ConnectionBackend, _defaultOptions: RequestOptions,accountEventsService:UserEventsService) {
        super(_backend,_defaultOptions);
        this.userEventsService = accountEventsService;
    }
    addSecurityHeader(url:string,method:string,options: RequestOptionsArgs,body: any):void {

        if(AppConst.UrlMatcher.matches(url)) {

            let securityToken:SecurityToken = new SecurityToken(JSON.parse(localStorage.getItem(AppConst.STORAGE_SECURITY_TOKEN)));
            let date:string = new Date().toISOString();
            let secret:string = securityToken.publicSecret;

            let message = '';
            if (method === 'PUT' || method === 'POST' || method === 'PATCH') {
               message = method + body + url + date;
            } else {
                message = method + url + date;
            }
            options.headers.set(AppConst.CSRF_CLAIM_HEADER, localStorage.getItem(AppConst.CSRF_CLAIM_HEADER));

            if (securityToken.isEncoding('HmacSHA256')) {
                options.headers.set(AppConst.HEADER_X_DIGEST, CryptoJS.HmacSHA256(message, secret).toString());
            } else if (securityToken.isEncoding('HmacSHA1')) {
                options.headers.set(AppConst.HEADER_X_DIGEST, CryptoJS.HmacSHA1(message, secret).toString());
            } else if (securityToken.isEncoding('HmacMD5')) {
                options.headers.set(AppConst.HEADER_X_DIGEST, CryptoJS.HmacMD5(message, secret).toString());
            }
            options.headers.set(AppConst.HEADER_X_ONCE, date);

            console.log('url',url);
            console.log('message',message);
            console.log('secret',secret);
            console.log('hmac message',options.headers.get(AppConst.HEADER_X_DIGEST));
        }

    }
    setOptions(options?: RequestOptionsArgs):RequestOptionsArgs {
        if(!options) {
            options = {};
        }
        if(!options.headers) {
            options.headers = new Headers();
        }
        return options;
    }
    mapResponse(res:Response,observer:Observer<Response>):void {
        if(res.ok && res.headers) {
            let securityToken:SecurityToken = new SecurityToken(JSON.parse(localStorage.getItem(AppConst.STORAGE_SECURITY_TOKEN)));
            if(securityToken) {
                localStorage.setItem(AppConst.STORAGE_SECURITY_TOKEN,JSON.stringify(securityToken));
            }
        }
        observer.next(res);
        observer.complete();
    }
    catchResponse(res:Response,observer:Observer<Response>):void {
        if(res.status === 403) {
            console.log('Unauthorized request:',res.text());
            this.userEventsService.logout({error:res.text()});
        }
        observer.complete();
    }
    get(url: string, options?: RequestOptionsArgs): Observable<Response> {
        options = this.setOptions(options);
        this.addSecurityHeader(url,'GET',options,null);

        return Observable.create((observer:Observer<Response>) => {
            super.get(url, options)
                .subscribe((res:Response) => {
                    this.mapResponse(res,observer);
                },(res:Response) => {
                    this.catchResponse(res,observer);
                });
        });
    }
    post(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        options = this.setOptions(options);
        this.addSecurityHeader(url,'POST',options, body);

        return Observable.create((observer:Observer<Response>) => {
            super.post(url,body,options)
                .subscribe((res:Response) => {
                    this.mapResponse(res,observer);
                },(res:Response) => {
                    this.catchResponse(res,observer);
                });
        });
    }
    put(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        options = this.setOptions(options);
        this.addSecurityHeader(url,'PUT',options, body);

        return Observable.create((observer:Observer<Response>) => {
            super.put(url,body,options)
                .subscribe((res:Response) => {
                    this.mapResponse(res,observer);
                },(res:Response) => {
                    this.catchResponse(res,observer);
                });
        });
    }
}