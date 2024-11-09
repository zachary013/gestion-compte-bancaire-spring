import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OperationService {

  url = "http://localhost:8081" ;
  constructor(private httpClient:HttpClient) { }

  add(data:any){
    return this.httpClient.post(this.url+
      "/operations",data, {
      headers: new HttpHeaders().set('Content-Type','application/json')
    })
  }


  getOperations(){
    return this.httpClient.get(this.url + "/operations");
  }}
