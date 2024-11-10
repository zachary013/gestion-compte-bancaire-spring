import { Component, OnInit } from '@angular/core';
import { ComptesService } from '../comptes.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-comptes',
  templateUrl: './comptes.component.html',
  styleUrls: ['./comptes.component.scss']
})
export class ComptesComponent implements OnInit {

  comptes: any;
  compte: any;
  newCompte: any = {}; // Initialize an empty object for the new compte

  constructor(private comptesService: ComptesService, private router: Router) { }

  ngOnInit(): void {
    this.getAllComptes();  // Fetch all comptes on component load
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
          // Close the modal
          const addCompteModal = document.getElementById('addCompteModal') as any;
          addCompteModal?.modal('hide');
        },
        (error) => {
          console.error('Error adding compte:', error);
        }
      );
    }
  }
}
