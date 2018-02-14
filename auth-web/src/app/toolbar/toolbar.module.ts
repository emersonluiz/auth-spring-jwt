import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatToolbarModule, MatIconModule, MatButtonModule, MatTooltipModule, MatInputModule } from '@angular/material';

import { PrincipalComponent } from './principal/principal.component';
import { NavigationComponent } from './navigation/navigation.component';
import { ActionComponent } from './action/action.component';
import { SearchComponent } from './search/search.component';

@NgModule({
  imports: [
    CommonModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    MatInputModule,
    FormsModule
  ],
  declarations: [
    PrincipalComponent,
    NavigationComponent,
    ActionComponent,
    SearchComponent
  ],
  exports: [
    PrincipalComponent,
    NavigationComponent,
    ActionComponent,
    SearchComponent
  ]
})
export class ToolbarModule { }
