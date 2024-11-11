import { Injectable } from '@angular/core';
import {environment} from '../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  url = "http://localhost:8081" ;
  constructor(private httpClient:HttpClient) { }

  signup(data:any){
    return this.httpClient.post(this.url+
    "/register",data, {
      headers: new HttpHeaders().set('Content-Type','application/json')
    })
  }


  login(data:any){
    return this.httpClient.post(this.url+
      "/login",data, {
      headers: new HttpHeaders().set('Content-Type','application/json')
    })
  }



}
