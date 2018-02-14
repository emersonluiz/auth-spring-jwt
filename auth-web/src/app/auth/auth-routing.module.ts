import { ExpiredComponent } from './expired/expired.component';
import { RecoverComponent } from './recover/recover.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthComponent } from './auth.component';

const authRoutes: Routes = [
    { path:'', component: AuthComponent },
    { path:'forgot', component: ForgotPasswordComponent },
    { path:'recover/:token', component: RecoverComponent },
    { path:'expired', component: ExpiredComponent }
]

@NgModule({
    imports: [RouterModule.forChild(authRoutes)],
    exports: [RouterModule]
})
export class AuthRoutingModule { }
