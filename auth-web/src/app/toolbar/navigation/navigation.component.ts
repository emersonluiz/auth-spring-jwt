import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  onHome() {
    this.router.navigate(['home']);
  }

  onChangePassword() {
    this.router.navigate(['password']);
  }

  onUser() {
    this.router.navigate(['user']);
  }
}
