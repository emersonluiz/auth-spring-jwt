import { AppConfigService } from './../app-config.service';
import { HttpService } from './../http/http.service';
import { Injectable } from '@angular/core';

@Injectable()
export class UserService {

  private url: string;
  
  constructor(private httpService: HttpService,
              private appService: AppConfigService) {
    this.url = this.appService.baseUrl
  }

  create(personId: string, data: any) {
    return this.httpService.post(this.url + this.appService.personUrl + "/" + personId + this.appService.userUrl, data)
            .map(res => this.httpService.extractData(res))
            .catch(error => this.httpService.handleError(error))
  }

  get(personId: string) {
    return this.httpService.get(this.url + this.appService.personUrl + "/" + personId + this.appService.userUrl)
            .map(res => this.httpService.extractData(res))
            .catch(error => this.httpService.handleError(error))
  }

  delete(personId: string, userId: string) {
    return this.httpService.delete(this.url + this.appService.personUrl + "/" + personId + this.appService.userUrl + "/" + userId)
            .map(res => this.httpService.extractData(res))
            .catch(error => this.httpService.handleError(error))
  }

  update(personId: string, userId: string, data: any) {
    return this.httpService.put(this.url + this.appService.personUrl + "/" + personId + this.appService.userUrl + "/" + userId, data)
            .map(res => this.httpService.extractData(res))
            .catch(error => this.httpService.handleError(error))
  }
}
