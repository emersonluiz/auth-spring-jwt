import { AuthService } from './../auth.service';
import { Router } from '@angular/router';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ForgotPasswordComponent implements OnInit {

  username: string;
  sent: boolean = false;
  hasError = false;
  sending = false;

  constructor(private router: Router,
              private authService: AuthService) { }

  ngOnInit() {
  }

  onCancel() {
    this.router.navigate([""]);
  }

  forgot() {
    this.sending = true;
    this.hasError = false;
    this.authService.forgot({username: this.username}).subscribe(
      res => {
        this.sent = true;
        this.sending = false;
      },
      error => {
        this.sending = false;
        console.log(error)
        if(error.failureReason) {
          this.hasError = true;
        }
      }
    )
    
  }
}
