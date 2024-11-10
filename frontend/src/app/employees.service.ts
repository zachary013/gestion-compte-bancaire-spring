import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

// Définition des interfaces pour les types EmployeRequest et EmployeResponse
export interface EmployeRequest {
  nomEmploye: string;
  codeEmployeSuperieur?: number;
  codesGroupes?: number[];
}

export interface EmployeResponse {
  codeEmploye: number;
  nomEmploye: string;
  codeEmployeSuperieur?: number;
  nomEmployeSuperieur?: string;
  codesGroupes?: number[];
}

@Injectable({
  providedIn: 'root'
})
export class EmployeesService {
  private url = "http://localhost:8081/employes";
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private httpClient: HttpClient) {}

  // Ajoute un nouvel employé
  addEmployee(employee: EmployeRequest): Observable<EmployeResponse> {
    return this.httpClient.post<EmployeResponse>(this.url, employee, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  // Récupère la liste des employés
  getEmployees(): Observable<EmployeResponse[]> {
    return this.httpClient.get<EmployeResponse[]>(this.url)
      .pipe(catchError(this.handleError));
  }

  // Récupère un employé par son code
  getEmployee(codeEmploye: number): Observable<EmployeResponse> {
    return this.httpClient.get<EmployeResponse>(`${this.url}/${codeEmploye}`)
      .pipe(catchError(this.handleError));
  }

  // Supprime un employé par son code
  deleteEmploye(codeEmploye: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.url}/${codeEmploye}`)
      .pipe(catchError(this.handleError));
  }

  // Met à jour un employé
  updateEmploye(codeEmploye: number, data: EmployeRequest): Observable<EmployeResponse> {
    return this.httpClient.put<EmployeResponse>(`${this.url}/${codeEmploye}`, data, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  // Gestion des erreurs
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Une erreur inconnue est survenue';
    if (error.error instanceof ErrorEvent) {
      // Erreur côté client
      errorMessage = `Erreur côté client : ${error.error.message}`;
    } else {
      // Erreur côté serveur
      errorMessage = `Erreur du serveur : Code ${error.status}, Message : ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }
}
