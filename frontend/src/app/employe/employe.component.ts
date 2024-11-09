import {Component, OnInit} from '@angular/core';
import {ComptesService} from '../comptes.service';
import {Router} from '@angular/router';
import {EmployeesService} from '../employees.service';
import { Select2SearchEvent} from 'ng-select2-component';

@Component({
  selector: 'app-employe',
  templateUrl: './employe.component.html',
  styleUrl: './employe.component.scss'
})
export class EmployeComponent implements OnInit{

  employees: any ;
  employee: any ;
  showAlert: boolean = false;

  newEmployee : any = {
    "nomEmploye": '',
    "subEmployes": null
  }

  constructor(private employeesService: EmployeesService, private router:Router) {}

  ngOnInit(): void {
    this.getAllEmployees(); // Corrected to use 'this'
  }

  addEmployee(){
    this.employeesService.add(this.newEmployee).subscribe(
      (res: any) => {
        // Successfully added employee
        console.log("Employee added:", res);
        setTimeout(() => {
          this.showAlert = false;  // Hide the alert after 3 seconds
        }, 3000);
      },
      (error: any) => {
        // Handle error
        console.error("Error adding employee:", error);
      }
    );
  }

  getOneEmployee(codeEmploye: number){
    this.employeesService.getEmployee(codeEmploye).subscribe((res: any) => {
      this.employee = res ;
    })
  }

  getAllEmployees() {
    this.employeesService.getEmployees().subscribe((res: any) => {
      console.log("API response:", res); // Add this to see the structure of `res`
      this.employees = res;
    });
  }

  deleteEmployee(codeEmploye: number){
    this.employeesService.deleteEmploye(codeEmploye).subscribe((res: any) => {
      this.employees = res;
      this.getAllEmployees();  // Reload the employee list after deletion
      this.showAlert = true;  // Show the alert after deletion
      setTimeout(() => {
        this.showAlert = false;  // Hide the alert after 3 seconds
      }, 3000);
    });

  }





}
