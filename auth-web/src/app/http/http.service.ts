import { Router } from '@angular/router';
import { AuthService } from './../auth/auth.service';
import { CookieService } from './../cookie/cookie.service';
import { Injectable } from '@angular/core';
import { Http, Headers, Response, RequestOptions, ResponseContentType } from '@angular/http';

import { Observable } from 'rxjs/Rx';

import { AppConfigService } from '../app-config.service';

@Injectable()
export class HttpService {

  constructor(private http: Http,
              private appConfig:AppConfigService,
              private cookieService:CookieService,
              private authService: AuthService,
              private router: Router) { }

  createAuthorizationHeader(headers:Headers): any {
    headers.append('Authorization', ('Bearer ' + this.cookieService.getCookie("token")));
    let opt = new RequestOptions();
    opt.headers = headers;
    return opt;
  }

  get(url:string) {
    let headers = new Headers();
    let opt = this.createAuthorizationHeader(headers)

    let r = this.http.get(url, opt);
    return r;
  }

  post(url:string, data:any) {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    let opt = this.createAuthorizationHeader(headers);
    return this.http.post(url, data, opt);
  }

  put(url:string, data:any) {
    let headers = new Headers();
    headers.append('Authorization', 'Bearer ' + this.cookieService.getCookie("token"));
    let opt = new RequestOptions();
    opt.headers = headers; 

   // headers.append('Content-Type', 'application/json');
    //let opt = this.createAuthorizationHeader(headers);
    //console.log("XXX", opt)
    return this.http.put(url, data, {headers:headers});
  }

  delete(url:string) {
    let headers = new Headers();
    let opt = this.createAuthorizationHeader(headers);
    return this.http.delete(url, opt);
  }

  extractData(res: Response) {
    if (res.status < 200 || res.status >= 300) {
      console.log(res.status)
      throw new Error('Bad response status: ' + res.status)
    }
    if(res.text().length === 0) {
        return { }
    }
    let body = res.json()
    return body || { }
  }

  handleError (error: any) {
    if(error.status === 401) {
      this.authService.logout();
      this.router.navigate(['/']);
    }
    let errMsg = error.message || 'Server error'
    console.error(errMsg)
    return Observable.throw({error:errMsg, status:error.status})
  }

}