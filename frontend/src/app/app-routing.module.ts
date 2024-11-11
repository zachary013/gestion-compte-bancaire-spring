import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {EmployeComponent} from './employe/employe.component';
import {ComptesComponent} from './comptes/comptes.component';
import {ClientsComponent} from './clients/clients.component';
import {VirementComponent} from './virement/virement.component';
import {VersementComponent} from './versement/versement.component';
import {OperationComponent} from './operation/operation.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {GroupsComponent} from './groups/groups.component';
import {NotfoundComponent} from './notfound/notfound.component';
import {RetraitComponent} from './retrait/retrait.component';

const routes: Routes = [
  {path:'login',component:LoginComponent},
  {path:'register', component:RegisterComponent},
  {path:'dashboard', component:DashboardComponent},
  {path:'versement', component:VersementComponent},
  {path:'virement', component:VirementComponent},
  {path:'clients', component:ClientsComponent},
  {path:'comptes', component:ComptesComponent},
  {path:'operations', component:OperationComponent},
  {path:'employees', component:EmployeComponent},
  {path:'groups', component:GroupsComponent},
  {path:'retrait', component:RetraitComponent},
  {path: '**', component:NotfoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
