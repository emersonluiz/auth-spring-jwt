import { AlertDialogComponent } from './alert-dialog/alert-dialog.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatDialogModule, MatDialogContent, MatButtonModule} from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule
  ],
  declarations: [
    AlertDialogComponent
  ],
  entryComponents: [
    AlertDialogComponent
  ]
})
export class DialogModule { }
