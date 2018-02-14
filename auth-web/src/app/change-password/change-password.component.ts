import { AlertDialogComponent } from './../dialog/alert-dialog/alert-dialog.component';
import { ChangePasswordService } from './change-password.service';
import { Router } from '@angular/router';
import { AuthService } from './../auth/auth.service';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ChangePasswordComponent implements OnInit {

  username: string;
  password: string;
  password2: string;

  hide1: boolean = true;
  hide2: boolean = true;

  constructor(private authService: AuthService,
              private router: Router,
              private changePasswordService: ChangePasswordService,
              public dialog: MatDialog) {
              let user = authService.getUser();
              this.username = user.username; }

  ngOnInit() {
  }

  save() {
    if(this.password == this.password2) {
      if(this.verify()) {
        this.changePasswordService.update({username: this.username, password: this.password}).subscribe(
          res => {
            this.router.navigate(['home']);
          }
        )
      }
    }
  }

  isInvalid() {
    if(this.password === "" || this.password === undefined) {
      return true;
    } 

    if(this.password2 === "" || this.password2 === undefined) {
      return true;
    }

    if(this.password !== this.password2) {
      return true;
    }

    return false;
  }

  verify() {
    if(this.password.length >= 6) {
      
      let char = /[@!#$%&*+=?|-]/;
      let upper = /[A-Z]/;

      if (!char.test(this.password)) {
        let dialogRef = this.dialog.open(AlertDialogComponent);
      // $scope.popup("A special character is required!", "Error", 450, 200);
        return false;
      }

      if (!upper.test(this.password)) {
        let dialogRef = this.dialog.open(AlertDialogComponent);
        //$scope.popup("An uppercase character is required!", "Error", 450, 200);
        return false;
      }
    } else {
      let dialogRef = this.dialog.open(AlertDialogComponent);
      return false;
    }

    return true;
  }
}
