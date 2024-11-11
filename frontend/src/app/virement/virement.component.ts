import { Component, OnInit } from '@angular/core';
import { OperationService, OperationRequest, OperationResponse } from '../operation.service';
import { ClientService, ClientResponse, CompteResponse } from '../client.service';
import { EmployeesService, EmployeResponse } from '../employees.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-virement',
  templateUrl: './virement.component.html',
  styleUrls: ['./virement.component.scss']
})
export class VirementComponent implements OnInit {
  employees: EmployeResponse[] = [];
  clients: ClientResponse[] = [];
  sourceAccounts: CompteResponse[] = [];
  destinationAccounts: CompteResponse[] = [];
  selectedSourceClient?: number;
  selectedDestinationClient?: number;

  virementRequest: OperationRequest = {
    montant: 0,
    codeCompte: '',
    codeCompteDest: '',
    codeEmploye: 0,
    typeOperation: 'VIREMENT'
  };
  dateOperation: Date | undefined;
  remarks: string = '';
  virementResponse?: OperationResponse;

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
    if (this.virementRequest.codeEmploye) {
      this.fetchClients();
    }
  }

  onClientChange(type: 'source' | 'destination'): void {
    const selectedClient = type === 'source' ? this.selectedSourceClient : this.selectedDestinationClient;

    // Vérifier si le client est sélectionné et récupérer ses comptes
    if (selectedClient) {
      this.clientService.getClientAccounts(selectedClient).subscribe({
        next: (comptes) => {
          if (type === 'source') {
            this.sourceAccounts = comptes;
          } else {
            this.destinationAccounts = comptes;
          }
        },
        error: (err) => console.error(`Erreur lors de la récupération des comptes du client ${type}:`, err)
      });
    }
  }


  submitVirement(): void {
    if (this.virementRequest.montant && this.virementRequest.codeCompte && this.virementRequest.codeCompteDest && this.virementRequest.codeEmploye) {
      this.operationService.virement(this.virementRequest).subscribe({
        next: (response) => {
          this.virementResponse = response;
          Swal.fire({
            title: "Succès",
            text: "Virement effectué avec succès!",
            icon: "success"
          });
        },
        error: (err) =>  {
          console.error('Erreur lors du virement:', err)
          Swal.fire({
            title: "Erreur",
            text: "Erreur lors du virement!",
            icon: "error"
          });
        }
      });
    } else {
      alert('Veuillez remplir tous les champs requis pour le virement.');
    }
  }
}
