import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  url = "http://localhost:8081" ;
  constructor(private httpClient:HttpClient) { }

  getQuantities(){
    return this.httpClient.get(this.url + "/dashboard/quantities");
  }
}
