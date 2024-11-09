import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeesService {

  url = "http://localhost:8081" ;
  constructor(private httpClient:HttpClient) { }

  add(data:any): Observable<any>{
    return this.httpClient.post(this.url+
      "/employes",data, {
      headers: new HttpHeaders().set('Content-Type','application/json')
    })
  }

  addEmployee(employee: any) {
    return this.httpClient.post<any>(this.url+ "/employes", employee); // Adjust the URL for adding employee
  }

  getEmployees(){
    return this.httpClient.get(this.url + "/employes");
  }

  getEmployee(codeEmploye: number) {
    return this.httpClient.get(`${this.url}/employes/${codeEmploye}`);
  }

  deleteEmploye(codeEmploye: number){
    return this.httpClient.delete(`${this.url}/employes/${codeEmploye}`);
  }


}
