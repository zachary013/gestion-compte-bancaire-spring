import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

export interface ClientRequest {
  nomClient: string;
  email?: string;
  dateNaissance?: Date;
  telephone?: string;
  adresse?: string;
  ville?: string;
  pays?: string;
}

export interface CompteResponse {
  codeCompte: number;
  typeCompte: 'COURANT' | 'EPARGNE';
  solde: number;
  taux: number;
  decouvert: number;
  dateCreation: Date;
}

export interface ClientResponse {
  codeClient: number;
  nomClient: string;
  email: string;
  dateNaissance: Date;
  telephone: string;
  adresse: string;
  ville: string;
  pays: string;
  comptes: CompteResponse[];
}

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private url = "http://localhost:8081/clients";

  constructor(private httpClient: HttpClient) { }

  addClient(clientRequest: ClientRequest): Observable<ClientResponse> {
    return this.httpClient.post<ClientResponse>(this.url, clientRequest, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    }).pipe(
      catchError(error => {
        console.error("Error adding client:", error);
        return throwError(error);
      })
    );
  }

  getClients(): Observable<ClientResponse[]> {
    return this.httpClient.get<ClientResponse[]>(this.url).pipe(
      catchError(error => {
        console.error("Error fetching clients:", error);
        return throwError(error);
      })
    );
  }

  getClient(codeClient: number): Observable<ClientResponse> {
    return this.httpClient.get<ClientResponse>(`${this.url}/${codeClient}`).pipe(
      catchError(error => {
        console.error(`Error fetching client with ID ${codeClient}:`, error);
        return throwError(error);
      })
    );
  }

  getClientAccounts(codeClient: number): Observable<CompteResponse[]> {
    return this.httpClient.get<CompteResponse[]>(`${this.url}/${codeClient}/comptes`).pipe(
      catchError(error => {
        console.error(`Error fetching accounts for client with ID ${codeClient}:`, error);
        return throwError(error);
      })
    );
  }

  updateClient(codeClient: number, clientRequest: ClientRequest): Observable<ClientResponse> {
    return this.httpClient.put<ClientResponse>(`${this.url}/${codeClient}`, clientRequest, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    }).pipe(
      catchError(error => {
        console.error(`Error updating client with ID ${codeClient}:`, error);
        return throwError(error);
      })
    );
  }

  deleteClient(codeClient: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.url}/${codeClient}`).pipe(
      catchError(error => {
        console.error(`Error deleting client with ID ${codeClient}:`, error);
        return throwError(error);
      })
    );
  }
}
