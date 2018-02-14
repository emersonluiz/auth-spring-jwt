import { HttpService } from './../http/http.service';
import { AppConfigService } from './../app-config.service';
import { Injectable } from '@angular/core';

@Injectable()
export class ChangePasswordService {

  constructor(private httpService: HttpService,
              private appConfig: AppConfigService) { }

  update(user: any) {
    return this.httpService.put(this.appConfig.baseUrl + "/users/" + user.username, user)
      .map(res => this.httpService.extractData(res))
      .catch(error => this.httpService.handleError(error))
  }

}
