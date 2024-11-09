import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { EmployeComponent } from './employe/employe.component';
import { VersementComponent } from './versement/versement.component';
import { VirementComponent } from './virement/virement.component';
import { ClientsComponent } from './clients/clients.component';
import { ComptesComponent } from './comptes/comptes.component';
import {HttpClientModule} from '@angular/common/http';
import { OperationComponent } from './operation/operation.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { GroupsComponent } from './groups/groups.component';
import {Select2Module} from "ng-select2-component";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    EmployeComponent,
    VersementComponent,
    VirementComponent,
    ClientsComponent,
    ComptesComponent,
    OperationComponent,
    DashboardComponent,
    GroupsComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        Select2Module
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
