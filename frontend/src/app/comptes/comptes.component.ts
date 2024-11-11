import { Component, OnInit } from '@angular/core';
import { ComptesService } from '../comptes.service';
import { Router } from '@angular/router';
import {ClientService} from '../client.service';
import {EmployeesService} from '../employees.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-comptes',
  templateUrl: './comptes.component.html',
  styleUrls: ['./comptes.component.scss']
})
export class ComptesComponent implements OnInit {

  comptes: any;
  compte: any;
  newCompte: any = {
    "codeCompte": "",
    "dateCreation": "",
    "solde": 0,
    "operations": null,
    "decouvert": null,
    "codeClient": null,
    "codeEmploye": null
    };

  clients : any ;
  employees : any ;

  // Pagination variables
  currentPage: number = 1;
  itemsPerPage: number = 7;
  totalItems: number = 0;

  constructor(private comptesService: ComptesService, private clientService: ClientService, private employeesService: EmployeesService, private router: Router) { }

  ngOnInit(): void {
    this.getAllComptes();  // Fetch all comptes on component load
    this.fetchClients();
    this.fetchEmployees();
  }

  fetchClients(){
    this.clientService.getClients().subscribe({
      next: (res) => this.clients = res
    });
  }

  fetchEmployees(){
    this.employeesService.listEmployes().subscribe({
      next: (res) => this.employees = res
    });
  }


  // Fetch all comptes from the service
  getAllComptes() {
    this.comptesService.getComptes().subscribe((res: any) => {
      this.comptes = res;
      this.totalItems = this.comptes.length;
    });
  }

  // Fetch a single compte by codeCompte
  getOneCompte(codeCompte: string) {
    this.comptesService.getCompte(codeCompte).subscribe(
      (data) => {
        this.compte = data;
        console.log('Compte fetched:', data);  // Debugging step
      },
      (error) => {
        console.error('Error fetching compte:', error);
      }
    );
  }

  // Handle Add Compte form submission
  addCompte() {
    if (this.newCompte) {
      this.comptesService.add(this.newCompte).subscribe(
        (res) => {
          console.log('Compte added:', res);
          Swal.fire({
            title: "Succès",
            text: "Ajout effectué avec succès!",
            icon: "success"
          });
          this.getAllComptes(); // Refresh the list after adding
          this.newCompte = {
            "codeCompte": "",
            "dateCreation": "",
            "solde": 0,
            "operations": null,
            "decouvert": null,
            "codeClient": null,
            "codeEmploye": null
          };

        },
        (error) => {
          console.error('Error adding compte:', error);
          Swal.fire({
            title: "Erreur",
            text: "Erreur lors d'ajout de compte!",
            icon: "error"
          });
        }
      );
    }
  }

  updateCompte() {
    this.comptesService.update(this.compte.codeCompte, this.compte).subscribe({
      next: (res) => {
        Swal.fire({
          title: "Succès",
          text: "Modification effectué avec succès!",
          icon: "success"
        });
        this.getAllComptes();
        this.newCompte = {
          "codeCompte": "",
          "dateCreation": "",
          "solde": 0,
          "operations": null,
          "decouvert": null,
          "codeClient": null,
          "codeEmploye": null
        };

      },
      error: err => {
        Swal.fire({
          title: "Erreur",
          text: "Erreur lors de modification de compte!",
          icon: "error"
        });
      }
      }
    );
  }

  deleteCompte(codeCompte: string){
    this.comptesService.delete(codeCompte).subscribe({
      next:(res: any) => {
        Swal.fire({
          title: "Succès",
          text: "Suppression effectué avec succès!",
          icon: "success"
        });
        this.getAllComptes();
      },
      error: err => {
        Swal.fire({
          title: "Erreur",
          text: "Erreur lors de suppression de compte!",
          icon: "error"
        });

      }
    });
  }

  // Pagination methods
  onPageChange(page: number) {
    this.currentPage = page;
  }

  get paginatedComptes() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.comptes.slice(startIndex, startIndex + this.itemsPerPage);
  }

  get totalPages() {
    return Math.ceil(this.totalItems / this.itemsPerPage);
  }

  get pageNumbers(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }

  protected readonly Math = Math;
}
