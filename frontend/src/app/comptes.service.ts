import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ComptesService {

  url = "http://localhost:8081" ;
  constructor(private httpClient:HttpClient) { }

  add(data:any){
    return this.httpClient.post(this.url+
      "/comptes",data, {
      headers: new HttpHeaders().set('Content-Type','application/json')
    })
  }
  update(codeCompte: number, data: any): Observable<any> {
    return this.httpClient.put(this.url + "/comptes/" + codeCompte, data, {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    });
  }

  getComptes(){
    return this.httpClient.get(this.url + "/comptes");
  }

  getCompte(codeCompte: string) {
    return this.httpClient.get(`${this.url}/comptes/${codeCompte}`);
  }

  delete(codeCompte: string){
    return this.httpClient.delete(`${this.url}/comptes/${codeCompte}`);
  }

}
