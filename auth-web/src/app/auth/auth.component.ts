import { Router } from '@angular/router';
import { CookieService } from './../cookie/cookie.service';
import { AuthService } from './auth.service';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AuthComponent implements OnInit {

  hide = true;
  username: string = "";
  password: string = "";
  error: boolean = false;

  constructor(private authService: AuthService,
              private cookieService:CookieService,
              private router:Router) { }

  ngOnInit() {
    if(this.authService.isAuthenticated()) {
      this.router.navigate(['home'])
    }
  }

  login() {
    console.log('calling auth...')
    this.authService.login(this.username, this.password).subscribe(
      res => console.log("Login executed"),
      error => {
        this.error = true
      }
    )
    
  }

}
