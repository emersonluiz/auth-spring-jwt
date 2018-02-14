import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatToolbarModule, MatIconModule, MatButtonModule } from '@angular/material';

import { ToolbarModule } from './../toolbar/toolbar.module';
import { HomeComponent } from './home.component';
import { HomeRoutingModule } from './home-routing,module';

@NgModule({
  imports: [
    CommonModule,
    HomeRoutingModule,
    ToolbarModule
  ],
  declarations: [
    HomeComponent
  ]
})
export class HomeModule { }
