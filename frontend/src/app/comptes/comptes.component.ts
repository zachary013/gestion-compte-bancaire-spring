import { Component, OnInit } from '@angular/core';
import { ComptesService } from '../comptes.service';
import { Router } from '@angular/router';
import {ClientService} from '../client.service';
import {EmployeesService} from '../employees.service';

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
      console.log("API response:", res);  // Debugging step
      this.comptes = res;
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
        }
      );
    }
  }

  updateCompte() {
    this.comptesService.update(this.compte.codeCompte, this.compte).subscribe((res) => {
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

    });
  }

  deleteCompte(codeCompte: string){
    this.comptesService.delete(codeCompte).subscribe((res: any) => {
      this.getAllComptes();
    });
  }



}
