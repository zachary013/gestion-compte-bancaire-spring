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
  codeSupEmployee : number = -1 ;
  newEmployee : any = {
    "codeEmploye": null,
    "nomEmploye": '',
    "supEmployes": [],
    "comptes": [],
    "operations": []
  }

  constructor(private employeesService: EmployeesService, private router:Router) {}

  ngOnInit(): void {
    this.getAllEmployees(); // Corrected to use 'this'
  }

  addEmployee() {
    console.log("Before adding superior:", this.newEmployee.supEmployes);

    // Initialize supEmployes if it's null or undefined
    if (!this.newEmployee.supEmployes) {
      this.newEmployee.supEmployes = [];  // Ensure supEmployes is an array
    }

    // Get the supervising employee
    this.employeesService.getEmployee(this.codeSupEmployee).subscribe((res: any) => {
      if (res) {
        console.log("Adding supervisor to supEmployes:", res);
        this.newEmployee.supEmployes.push(res);  // Use push() to add the supervisor
      } else {
        console.error('Superior employee not found');
      }

      // Now submit the new employee
      this.employeesService.add(this.newEmployee).subscribe(
        (res: any) => {
          console.log("Employee added:", res);
          this.getAllEmployees();  // Reload employee list
          this.newEmployee = { "nomEmploye": '', "supEmployes": [] };  // Reset form fields
          this.showAlert = true;  // Show success alert
          setTimeout(() => { this.showAlert = false; }, 3000);  // Hide alert after 3 seconds
        },
        (error: any) => {
          console.error("Error adding employee:", error);
        }
      );
    });
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


  protected readonly Number = Number;
}
