import {Component, OnInit} from '@angular/core';
import {ClientService} from '../client.service';
import {Router} from '@angular/router';
import {ComptesService} from '../comptes.service';

@Component({
  selector: 'app-comptes',
  templateUrl: './comptes.component.html',
  styleUrl: './comptes.component.scss'
})
export class ComptesComponent implements OnInit{

  comptes: any ;
  compte: any ;

  constructor(private comptesService: ComptesService, private router:Router) {}

  ngOnInit(): void {
    this.getAllComptes(); // Corrected to use 'this'
  }

  getAllComptes() {
    this.comptesService.getComptes().subscribe((res: any) => {
      console.log("API response:", res); // Add this to see the structure of `res`
      this.comptes = res;
    });
  }

  getOneCompte(codeCompte: string){
    this.comptesService.getCompte(codeCompte).subscribe(
      (data) => this.compte = data,
      (error) => console.error('Error fetching compte:', error)
    );
  }

}
