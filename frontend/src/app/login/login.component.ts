import {Component, OnInit} from '@angular/core';
import {ClientService} from '../client.service';
import {Router} from '@angular/router';
import {UserService} from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{

  constructor(private userService: UserService, private router:Router) {}

  user : any = {
    "email": "",
    "password": "",
    "role": "",
    "createdAt": null
  };


  ngOnInit(): void {
  }

  login(user: any) {
    console.log('Login request:', user);
    this.userService.login(user).subscribe({
      next: (res: any) => {
        console.log("API response:", res);
        this.user = res;
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error("Login failed:", err);
        alert('Login failed. Please check your credentials and try again.');
      }
    });
  }



}
