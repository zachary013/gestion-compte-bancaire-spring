import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js/auto';
import {GroupsService} from '../groups.service';
import {Router} from '@angular/router';
import {DashboardService} from '../dashboard.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(private dashboardService: DashboardService, private router:Router) { }


  quantities : any = {
    "Compte": null,
    "Groupe": null,
    "Operation": null,
    "Client": null,
    "Employe": null
  }


  ngOnInit(): void {
    this.getQuantities();
  }

  getQuantities(): void {
    this.dashboardService.getQuantities().subscribe((res: any) => {
      this.quantities = res;
      this.createBarChart();
      this.createDoughnutChart();  // Create the charts after data is loaded
    });
  }




  // Create a Bar Chart for the first "Stats" card
  createBarChart(): void {
    const barChartCtx = document.getElementById('myBarChart') as HTMLCanvasElement;

    new Chart(barChartCtx, {
      type: 'bar',
      data: {
        labels: ['Employés', 'Groupes'],
        datasets: [{
          label: 'Quantité',
          data: [this.quantities.Employe, this.quantities.Groupe],
          borderWidth: 1,
          backgroundColor: 'rgba(54, 162, 235, 0.3)',
          borderColor: 'rgba(54, 162, 235, 1)',
        }]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }

  // Create a Doughnut Chart for the second "Stats" card
  createDoughnutChart(): void {
    const doughnutChartCtx = document.getElementById('myDoughnutChart') as HTMLCanvasElement;

    new Chart(doughnutChartCtx, {
      type: 'doughnut',
      data: {
        labels: ['Opérations', 'Comptes', 'Clients'],
        datasets: [{
          label: 'Votes Distribution',
          data: [this.quantities.Operation, this.quantities.Compte, this.quantities.Client],
          backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
          ],
          borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
          ],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            position: 'top',
          },
          tooltip: {
            callbacks: {
              label: (tooltipItem) => {
                return tooltipItem.label + ': ' + tooltipItem.raw + ' votes';
              }
            }
          }
        }
      }
    });
  }
}
