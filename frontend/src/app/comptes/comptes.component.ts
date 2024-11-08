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
  constructor(private compteservice: ComptesService, private router:Router) {}

  ngOnInit(): void {
    this.getAllComptes(); // Corrected to use 'this'
  }

  getAllComptes() {
    this.compteservice.getComptes().subscribe((res: any) => {
      console.log("API response:", res); // Add this to see the structure of `res`
      this.comptes = res;
    });
  }

}
