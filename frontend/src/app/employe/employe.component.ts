import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EmployeesService } from '../employees.service';

@Component({
  selector: 'app-employe',
  templateUrl: './employe.component.html',
  styleUrls: ['./employe.component.scss']
})
export class EmployeComponent implements OnInit {

  employees: any;
  employee: any;
  showAlert: boolean = false;
  codeSupEmployee: number = -1;
  newEmployee: any = {
    "codeEmploye": null,
    "nomEmploye": '',
    "codeEmployeSuperieur": null,
    "operations": []
  };
  editingEmployeeId: number | null = null;
  groups: any[] = [];

  constructor(private employeesService: EmployeesService, private router: Router) {}

  ngOnInit(): void {
    this.getAllEmployees();
  }

  addEmployee() {
    this.employeesService.addEmployee(this.newEmployee).subscribe(
      (res: any) => {
        this.getAllEmployees();
        this.newEmployee = { "nomEmploye": '', "codeEmployeSuperieur": null };
        this.showAlert = true;
        setTimeout(() => { this.showAlert = false; }, 3000);
      },
      (error: any) => {
        console.error("Error adding employee:", error);
      }
    );
  }

  getOneEmployee(codeEmploye: number) {
    this.employeesService.getEmployee(codeEmploye).subscribe((res: any) => {
      this.employee = res;
    });
  }

  getAllEmployees() {
    this.employeesService.getEmployees().subscribe((res: any) => {
      this.employees = res;
    });
  }

  deleteEmployee(codeEmploye: number) {
    this.employeesService.deleteEmploye(codeEmploye).subscribe(() => {
      this.getAllEmployees();
      this.showAlert = true;
      setTimeout(() => {
        this.showAlert = false;
      }, 3000);
    });
  }
}
