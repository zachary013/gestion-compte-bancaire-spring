import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface OperationRequest {
  montant: number;
  codeCompte: string;
  codeCompteDest?: string;
  codeEmploye: number;
  typeOperation?: string;
}

export interface OperationResponse {
  codeOperation: number;
  dateOperation: Date;
  montant: number;
  type: string;
  codeCompte: string;
  codeCompteDest?: string;
  soldeApresOperation: number;
  nomEmploye: string;
}

@Injectable({
  providedIn: 'root'
})
export class OperationService {
  private baseUrl = 'http://localhost:8081/operations';

  constructor(private http: HttpClient) { }

  getAllOperations(): Observable<OperationResponse[]> {
    return this.http.get<OperationResponse[]>(this.baseUrl);
  }

  getOperationsByCompte(codeCompte: string): Observable<OperationResponse[]> {
    return this.http.get<OperationResponse[]>(`${this.baseUrl}/${codeCompte}`);
  }

  virement(request: OperationRequest): Observable<OperationResponse> {
    return this.http.post<OperationResponse>(`${this.baseUrl}/virement`, request);
  }

  verser(request: OperationRequest): Observable<OperationResponse> {
    return this.http.post<OperationResponse>(`${this.baseUrl}/verser`, request);
  }

  retirer(request: OperationRequest): Observable<OperationResponse> {
    return this.http.post<OperationResponse>(`${this.baseUrl}/retirer`, request);
  }
}
