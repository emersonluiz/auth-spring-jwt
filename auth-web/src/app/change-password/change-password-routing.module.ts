import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ChangePasswordComponent } from './change-password.component';

const passwordChangeRoutes: Routes = [
    { path:'', component: ChangePasswordComponent }
]

@NgModule({
    imports: [RouterModule.forChild(passwordChangeRoutes)],
    exports: [RouterModule]
})
export class ChangePasswordRoutingModule { }
