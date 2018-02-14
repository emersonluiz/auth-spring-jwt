import { Injectable } from '@angular/core';

@Injectable()
export class AppConfigService {

  constructor() { }

  private privateZipcodeUrl = "https://viacep.com.br/ws";
  private privateBaseUrl: string = 'http://localhost:8080';
  private privatePersonUrl: string = '/pessoas';
  private privateUserUrl: string = '/usuarios';
  private privateAuthUrl: string = '/login';

  public get baseUrl(): string {
    return this.privateBaseUrl
  }

  public get personUrl(): string {
    return this.privatePersonUrl;
  }

  public get userUrl(): string {
    return this.privateUserUrl;
  }

  public get zipcodeUrl(): string {
    return this.privateZipcodeUrl;
  }

  public get authUrl(): string {
    return this.privateAuthUrl;
  }
}
