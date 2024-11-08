import {Component, OnInit} from '@angular/core';
import {UserService} from '../user.service';
import {Router} from '@angular/router';
import {ClientService} from '../client.service';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrl: './clients.component.scss'
})
export class ClientsComponent implements OnInit{

  clients: any ;
  constructor(private clientservice: ClientService, private router:Router) {}

  ngOnInit(): void {
    this.getAllClients(); // Corrected to use 'this'
  }

  getAllClients() {
    this.clientservice.getClients().subscribe((res: any) => {
      console.log("API response:", res); // Add this to see the structure of `res`
      this.clients = res;
    });
  }


}
