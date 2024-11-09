import { Component } from '@angular/core';
import {ClientService} from '../client.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrl: './operation.component.scss'
})
export class OperationComponent {
  clients: any ;
  constructor(private clientservice: ClientService, private router:Router) {}

  ngOnInit(): void {
    this.getAllOperations(); // Corrected to use 'this'
  }

  getAllOperations() {
    this.clientservice.getClients().subscribe((res: any) => {
      console.log("API response:", res); // Add this to see the structure of `res`
      this.clients = res;
    });
  }
}
