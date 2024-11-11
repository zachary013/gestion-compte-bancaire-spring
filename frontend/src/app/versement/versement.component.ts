import { Component, OnInit } from '@angular/core';
import { OperationRequest, OperationService } from '../operation.service';
import { ClientResponse, ClientService } from '../client.service';
import { EmployeesService, EmployeResponse } from '../employees.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-versement',
  templateUrl: './versement.component.html',
})
export class VersementComponent implements OnInit {
  versementRequest: OperationRequest = {
    montant: 0,
    codeCompte: '',
    codeEmploye: 0,
  };

  dateOperation: string = '';
  selectedClient: string = ''; // Keep this for selected client
  selectedAccount: string = ''; // Added to hold the selected account
  clients: ClientResponse[] = [];
  comptes: any[] = []; // Populate based on selected client
  employees: EmployeResponse[] = [];

  constructor(
    private operationService: OperationService,
    private clientService: ClientService,
    private employeesService: EmployeesService
  ) {}

  ngOnInit(): void {
    this.fetchEmployees();
    this.fetchClients(); // Fetch clients when the component is initialized
  }

  fetchEmployees(): void {
    this.employeesService.listEmployes().subscribe({
      next: (data) => {
        this.employees = data;
      },
      error: (err) => console.error('Erreur lors de la récupération des employés:', err),
    });
  }

  fetchClients(): void {
    this.clientService.getClients().subscribe({
      next: (data) => {
        this.clients = data;
        // Filter clients based on selectedClient if it's set
        if (this.selectedClient) {
          this.clients = this.clients.filter(
            (client) => client.codeClient === +this.selectedClient
          );
        }
      },
      error: (err) => console.error('Erreur lors de la récupération des clients:', err),
    });
  }

  // Fetch accounts based on the selected client
  fetchAccounts(): void {
    const selectedClientObj = this.clients.find(
      (client) => client.codeClient === +this.selectedClient
    );
    if (selectedClientObj) {
      this.comptes = selectedClientObj.comptes || [];
    }
  }

  submitVersement(): void {
    // Populate the versementRequest object with data from the form
    this.versementRequest.codeCompte = this.selectedAccount;
    this.operationService.verser(this.versementRequest).subscribe({
      next: (response) => {
        console.log('Versement effectué:', response);
        Swal.fire({
          title: "Succès",
          text: "Versement effectué avec succès!",
          icon: "success"
        });
      },
      error: (err) => {
        console.log("Erreur lors du versement:" + err);
        Swal.fire({
          title: "Erreur",
          text: "Erreur lors du versement!",
          icon: "error"
        });
      }
    });
  }
}
