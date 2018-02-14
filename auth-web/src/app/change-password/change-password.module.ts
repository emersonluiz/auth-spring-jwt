import { DialogModule } from './../dialog/dialog.module';
import { ChangePasswordService } from './change-password.service';
import { MatInputModule, MatIconModule, MatCardModule, MatButtonModule, MatFormFieldModule } from '@angular/material';
import { FormsModule } from '@angular/forms';
import { ChangePasswordRoutingModule } from './change-password-routing.module';
import { ToolbarModule } from './../toolbar/toolbar.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChangePasswordComponent } from './change-password.component';

@NgModule({
  imports: [
    CommonModule,
    ChangePasswordRoutingModule,
    ToolbarModule,
    FormsModule,
    MatInputModule,
    MatCardModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    DialogModule
  ],
  declarations: [
    ChangePasswordComponent
  ],
  providers: [
    ChangePasswordService
  ]
})
export class ChangePasswordModule { }
