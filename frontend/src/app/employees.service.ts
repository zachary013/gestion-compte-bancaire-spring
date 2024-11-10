import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

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
  private apiUrl = 'http://localhost:8081/employes'; // Update this URL to match your Spring Boot server
  private httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {}

  // Create a new employee
  saveEmploye(employeRequest: EmployeRequest): Observable<EmployeResponse> {
    return this.http.post<EmployeResponse>(this.apiUrl, employeRequest, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  // Update an existing employee
  updateEmploye(codeEmploye: number, employeRequest: EmployeRequest): Observable<EmployeResponse> {
    return this.http.put<EmployeResponse>(`${this.apiUrl}/${codeEmploye}`, employeRequest, this.httpOptions)
      .pipe(catchError(this.handleError));
  }

  // Get all employees
  listEmployes(): Observable<EmployeResponse[]> {
    return this.http.get<EmployeResponse[]>(this.apiUrl)
      .pipe(catchError(this.handleError));
  }

  // Get a specific employee by code
  getEmploye(codeEmploye: number): Observable<EmployeResponse> {
    return this.http.get<EmployeResponse>(`${this.apiUrl}/${codeEmploye}`)
      .pipe(catchError(this.handleError));
  }

  // Delete an employee
  deleteEmploye(codeEmploye: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codeEmploye}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred';
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Client-side error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = `Server-side error: Code ${error.status}, Message: ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
