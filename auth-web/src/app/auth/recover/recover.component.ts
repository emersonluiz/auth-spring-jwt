import { ChangePasswordService } from './../../change-password/change-password.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from './../auth.service';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';

import { MatDialog } from '@angular/material';
import { AlertDialogComponent } from './../../dialog/alert-dialog/alert-dialog.component';

@Component({
  selector: 'app-recover',
  templateUrl: './recover.component.html',
  styleUrls: ['./recover.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RecoverComponent implements OnInit {

  hide1: boolean = true;
  hide2: boolean = true;
  validation: boolean = true;
  error: boolean = false;

  username: string;
  password: string;
  password2: string;

  user: any;
  token: string;

  constructor(private authService: AuthService,
              private route: ActivatedRoute,
              private router:Router,
              public dialog: MatDialog,
              private changePasswordService: ChangePasswordService) { 
              }

  ngOnInit() {
    this.route.params.subscribe(
      (params: any) => {
        let token = params["token"];
        
        if (token !== undefined && token !== '') {
          this.verifyToken(token)
        }
      }
    );
  }

  verifyToken(token: string) {
    this.authService.recover(token).subscribe(
      res => {
        res.subscribe(
          rtn => {
            this.username = rtn.username;
            this.user = rtn;
            this.token = token;
            this.validation = false;
          }
        )
      },
      err => {
        this.error = true;
      }
    )
  }

  onBack() {
    this.router.navigate(['']);
  }

  onHelp() {
    let dialogRef = this.dialog.open(AlertDialogComponent);
  }

  save() {
    if(this.password == this.password2) {
      if(this.verify()) {
        this.authService.setSession(this.user, this.token).subscribe(
          r => {
            this.changePasswordService.update({username: this.username, password: this.password}).subscribe(
              res => {
                this.router.navigate(['home']);
              },
              er => {
                this.authService.logout();
                this.router.navigate(['']);
              }
            )
          },
          error => {
            this.authService.logout();
            this.router.navigate(['']);
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
        return false;
      }

      if (!upper.test(this.password)) {
        let dialogRef = this.dialog.open(AlertDialogComponent);
        return false;
      }
    } else {
      let dialogRef = this.dialog.open(AlertDialogComponent);
      return false;
    }

    return true;
  }
}
