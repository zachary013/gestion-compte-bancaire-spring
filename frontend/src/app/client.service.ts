import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private url = "http://localhost:8081";

  constructor(private httpClient: HttpClient) { }

  add(data: any): Observable<any> {
    return this.httpClient.post(`${this.url}/clients`, data, {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    });
  }

  getClients(): Observable<any> {
    return this.httpClient.get(`${this.url}/clients`);
  }

  getClient(id: number): Observable<any> {
    return this.httpClient.get(`${this.url}/clients/${id}`);
  }

  updateClient(id: number, data: any): Observable<any> {
    return this.httpClient.put(`${this.url}/clients/${id}`, data, {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    });
  }

  deleteClient(id: number): Observable<any> {
    return this.httpClient.delete(`${this.url}/clients/${id}`);
  }
}
