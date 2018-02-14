import { AuthService } from './../../auth/auth.service';
import { Router } from '@angular/router';
import { CookieService } from './../../cookie/cookie.service';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent implements OnInit {

  @Input() title: string;
  userName: string = "";

  constructor(private cookieService: CookieService, 
              private router: Router,
              private authService: AuthService) {
    this.userName = cookieService.getCookie('username');
  }

  ngOnInit() {
  }

  onExit() {
    this.authService.logout();
    this.router.navigate(['']);
  }

}
