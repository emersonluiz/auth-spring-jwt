import { async } from '@angular/core/testing';
import { Router } from '@angular/router';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { AppConfigService } from './../app-config.service';
import { CookieService } from './../cookie/cookie.service';
import { HttpService } from './../http/http.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class AuthService {

  private tokenRequest:any
  private authUrl: string

  constructor(private http: Http,
              private cookieService:CookieService,
              private appConfig: AppConfigService,
              private router:Router) {
    this.authUrl = this.appConfig.baseUrl + this.appConfig.authUrl
  }

  isAuthenticated(): boolean {
    if(this.cookieService.getCookie('token')) {
        return true;
    } else {
        return false;
    }
  }

  forgot(user: any) {
    return this.http.post(this.appConfig.baseUrl + "/recovers", user)
      .map(res => this.extractData(res))
      .catch(error => this.handleError(error))
  }

  recover(token: string) {
    let headers = new Headers();
   // headers.append('Authorization', ('Bearer ' + token));

    let opt = new RequestOptions();
    opt.headers = headers;

    return this.http.post(this.appConfig.baseUrl + "/recovers/" + token , null, opt).map(res => {

      let token: string = res.headers.get('x-auth-token');

      return this.getUserByToken(token)
      .map(res => res)
      .catch(er => er)
    })
    .catch(error => this.handleError(error))
  }

  getUserByToken(token: string) {
    let header = new Headers();
    header.append('Authorization', "Bearer " + token);
    let opt = new RequestOptions();
    opt.headers = header;

    return this.http.get(this.appConfig.baseUrl + "/tokens" , opt)
    .map(res => this.extractData(res))
    .catch(error => this.handleError(error))
  }

  login(username: string, password: string) {
      return this.authentication(username, password)
  }

  authentication(username: string, password: string) {
    let headers = new Headers();
    headers.append('Authorization', ('Basic ' + btoa(username + ':' + password)));

    let opt = new RequestOptions();
    opt.headers = headers;

    return this.http.post(this.authUrl, null, opt).map(res => {

      let token: string = res.headers.get('x-auth-token');

      this.authorization(token).subscribe(
        res => {
          console.log('sendCredentials completed');
        }
      )

    })
    .catch(error => this.handleError(error))
  }

  authorization(token: string) {
    let header = new Headers();
    header.append('Authorization', "Bearer " + token);
    let opt = new RequestOptions();
    opt.headers = header;

    return this.http.get(this.appConfig.baseUrl + "/tokens" , opt).map(
      res => {
        let data = this.extractData(res);

        this.cookieService.setCookie('username', data.username);
        this.cookieService.setCookie('name', data.name);
        this.cookieService.setCookie('email', data.email);
        this.cookieService.setCookie("id", data.id)
        this.cookieService.setCookie('token', token);

        this.router.navigate(['/home']);
    })
    .catch(error => this.handleError(error))
  }

  setSession(data: any, token: string) {
    return Observable.create(
      obs => {
        this.cookieService.setCookie('username', data.username);
        this.cookieService.setCookie('name', data.name);
        this.cookieService.setCookie('email', data.email);
        this.cookieService.setCookie("id", data.id)
        this.cookieService.setCookie('token', token);
        obs.next();
      }
    )
  }

  logout() {
    this.cookieService.deleteCookie('id');
    this.cookieService.deleteCookie('username');
    this.cookieService.deleteCookie('name');
    this.cookieService.deleteCookie('email');
    this.cookieService.deleteCookie('token');
  }

  getUser() {
    return {id: this.cookieService.getCookie('id'), name: this.cookieService.getCookie('name'), username: this.cookieService.getCookie('username'), email: this.cookieService.getCookie('email')}
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
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);

      if(error.status === 401) {
        if(body !== '') {
          if(body.message.indexOf("User expired") !== -1) {
            this.router.navigate(['expired']);
          }
        }
      }

      errMsg = body;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }

}
