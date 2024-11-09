import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GroupsService {

  url = "http://localhost:8081" ;
  constructor(private httpClient:HttpClient) { }

  add(data:any): Observable<any>{
    return this.httpClient.post(this.url+
      "/groupes",data, {
      headers: new HttpHeaders().set('Content-Type','application/json')
    })
  }

  update(codeGroupe: number, data: any): Observable<any> {
    return this.httpClient.put(this.url + "/groupes/" + codeGroupe, data, {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    });
  }



  getGroupes(){
    return this.httpClient.get(this.url + "/groupes");
  }

  delete(codeGroup: any){
    return this.httpClient.delete(`${this.url}/groupes/${codeGroup}`);
  }
}
