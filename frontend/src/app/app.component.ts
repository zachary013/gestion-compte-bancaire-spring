import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  isSidebarCollapsed: boolean = false;
  isAuthPage: boolean = false;

  menuItems = [
    { label: 'Dashboard', route: '/dashboard', icon: 'fa-solid fa-chart-simple' },
    { label: 'Employees', route: '/employees', icon: 'fa-solid fa-user-tie' },
    { label: 'Clients', route: '/clients', icon: 'fa-solid fa-user' },
    { label: 'Comptes', route: '/comptes', icon: 'fa-solid fa-file-invoice' },
    { label: 'Groups', route: '/groups', icon: 'fa-solid fa-users' },
    { label: 'Versement', route: '/versement', icon: 'fa-solid fa-money-bill' },
    { label: 'Retrait', route: '/retrait', icon: 'fa-solid fa-money-bill-wave' },
    { label: 'Virements', route: '/virement', icon: 'fa-solid fa-money-bill-transfer' },
    { label: 'Operations', route: '/operations', icon: 'fa-solid fa-file-invoice-dollar' },
  ];


  toggleSidebar() {
    this.isSidebarCollapsed = !this.isSidebarCollapsed;
  }

  constructor(private router: Router) {}

  ngOnInit() {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Check if the current URL contains 'login' or 'register'
        this.isAuthPage = ['/login', '/register'].includes(event.url);
      }
    });
  }
}
