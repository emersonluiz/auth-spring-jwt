import { ChangePasswordModule } from './../change-password/change-password.module';
import { DialogModule } from './../dialog/dialog.module';
import { AuthService } from './auth.service';
import { FormsModule } from '@angular/forms';
import { MatIconModule, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatProgressSpinnerModule, MatTooltipModule } from '@angular/material';
import { AuthComponent } from './auth.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { RecoverComponent } from './recover/recover.component';
import { ExpiredComponent } from './expired/expired.component';

@NgModule({
  imports: [
    CommonModule,
    AuthRoutingModule,
    MatIconModule,
    FormsModule,
    MatCardModule,
    MatTooltipModule,
    DialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    ChangePasswordModule
  ],
  declarations: [
    AuthComponent,
    ForgotPasswordComponent,
    RecoverComponent,
    ExpiredComponent
  ],
  providers: [
    AuthService
  ]
})
export class AuthModule { }
