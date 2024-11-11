import { Component } from '@angular/core';
import { EmployeesService, EmployeResponse } from '../employees.service';
import { ClientResponse, ClientService, CompteResponse } from '../client.service';
import { OperationRequest, OperationResponse, OperationService } from '../operation.service';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-retrait',
  templateUrl: './retrait.component.html',
  styleUrls: ['./retrait.component.scss']
})
export class RetraitComponent {
  employees: EmployeResponse[] = [];
  clients: ClientResponse[] = [];
  sourceAccounts: CompteResponse[] = [];
  selectedClient?: number;

  retraitRequest: OperationRequest = {
    montant: 0,
    codeCompte: '',
    codeEmploye: 0,
    typeOperation: 'RETRAIT'  // Set operation type to RETRAIT
  };
  dateOperation: Date | undefined;
  remarks: string = '';
  retraitResponse?: OperationResponse;

  constructor(
    private operationService: OperationService,
    private clientService: ClientService,
    private employeesService: EmployeesService
  ) {}

  ngOnInit(): void {
    this.fetchEmployees();
  }

  fetchEmployees(): void {
    this.employeesService.listEmployes().subscribe({
      next: (data) => this.employees = data,
      error: (err) => console.error('Erreur lors de la récupération des employés:', err)
    });
  }

  fetchClients(): void {
    this.clientService.getClients().subscribe({
      next: (data) => this.clients = data,
      error: (err) => console.error('Erreur lors de la récupération des clients:', err)
    });
  }

  onEmployeeChange(): void {
    if (this.retraitRequest.codeEmploye) {
      this.fetchClients();
    }
  }

  onClientChange(): void {
    if (this.selectedClient) {
      this.clientService.getClientAccounts(this.selectedClient).subscribe({
        next: (comptes) => {
          this.sourceAccounts = comptes;
        },
        error: (err) => console.error('Erreur lors de la récupération des comptes du client:', err)
      });
    }
  }

  submitRetrait(): void {
    if (this.retraitRequest.montant && this.retraitRequest.codeCompte && this.retraitRequest.codeEmploye) {
      this.operationService.retirer(this.retraitRequest).subscribe({
        next: (response) => {
          Swal.fire({
            title: "Good job!",
            text: "You clicked the button!",
            icon: "success"
          });
          //this.retraitResponse = response;
        },
        error: (err) => {
          console.error('Erreur lors du retrait:', err);
          Swal.fire({
            title: "Erreur!",
            text: 'Erreur lors du retrait',
            icon: "error"
          });
        }
      });
    } else {
      alert('Veuillez remplir tous les champs requis pour le retrait.');
    }
  }
}
