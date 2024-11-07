import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {EmployeComponent} from './employe/employe.component';
import {ComptesComponent} from './comptes/comptes.component';
import {ClientsComponent} from './clients/clients.component';
import {VirementComponent} from './virement/virement.component';
import {VersementComponent} from './versement/versement.component';

const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'register', component:RegisterComponent},
  {path:'dashboard', component:EmployeComponent},
  {path:'versement', component:VersementComponent},
  {path:'virement', component:VirementComponent},
  {path:'clients', component:ClientsComponent},
  {path:'comptes', component:ComptesComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
