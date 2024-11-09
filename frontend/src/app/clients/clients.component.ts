import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ClientService } from '../client.service';
import { Modal } from 'bootstrap';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.scss']
})
export class ClientsComponent implements OnInit {
  @ViewChild('editForm') editForm!: NgForm;
  @ViewChild('addForm') addForm!: NgForm;

  clients: any[] = [];
  selectedClient: any = null;
  clientToDelete: any = null;
  newClient: any = {
    nomClient: '',
    email: '',
    dateNaissance: '',
    telephone: '',
    adresse: '',
    ville: '',
    pays: ''
  };
  errorMessage: string = '';

  private modals: { [key: string]: Modal } = {};

  constructor(private clientService: ClientService) {}

  ngOnInit(): void {
    this.getAllClients();
    this.initializeModals();
  }

  private initializeModals() {
    ['deleteModal', 'detailsModal', 'editModal', 'addClientModal'].forEach(modalId => {
      const modalElement = document.getElementById(modalId);
      if (modalElement) {
        this.modals[modalId] = new Modal(modalElement);
      }
    });
  }

  getAllClients() {
    this.clientService.getClients().subscribe({
      next: (res) => this.clients = res,
      error: (error) => this.errorMessage = 'Erreur lors du chargement des clients'
    });
  }

  openAddClientModal() {
    this.newClient = {
      nomClient: '',
      email: '',
      dateNaissance: '',
      telephone: '',
      adresse: '',
      ville: '',
      pays: ''
    };
    this.modals['addClientModal']?.show();
  }

  addClient() {
    if (this.addForm.valid) {
      this.clientService.add(this.newClient).subscribe({
        next: () => {
          this.getAllClients();
          this.modals['addClientModal']?.hide();
          this.newClient = { nomClient: '', email: '', dateNaissance: '', telephone: '', adresse: '', ville: '', pays: '' };
        },
        error: () => this.errorMessage = "Erreur lors de l'ajout du client"
      });
    }
  }

  viewDetails(client: any) {
    this.selectedClient = {...client};
    this.modals['detailsModal']?.show();
  }

  editClient(client: any) {
    this.selectedClient = {...client};
    this.modals['editModal']?.show();
  }

  saveEditedClient() {
    if (this.selectedClient && this.editForm.valid) {
      this.clientService.updateClient(this.selectedClient.codeClient, this.selectedClient)
        .subscribe({
          next: () => {
            this.getAllClients();
            this.modals['editModal']?.hide();
            this.selectedClient = null;
          },
          error: () => this.errorMessage = 'Erreur lors de la mise à jour du client'
        });
    }
  }

  deleteClientConfirm(client: any) {
    this.clientToDelete = client;
    this.modals['deleteModal']?.show();
  }

  confirmDelete() {
    if (this.clientToDelete) {
      this.clientService.deleteClient(this.clientToDelete.codeClient).subscribe({
        next: () => {
          this.getAllClients();
          this.modals['deleteModal']?.hide();
          this.clientToDelete = null;
        },
        error: () => this.errorMessage = 'Erreur lors de la suppression du client'
      });
    }
  }

  ngOnDestroy() {
    Object.values(this.modals).forEach(modal => modal.dispose());
  }
}
