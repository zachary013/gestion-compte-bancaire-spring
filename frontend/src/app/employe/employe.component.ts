import { Component, OnInit } from '@angular/core';
import { EmployeesService, EmployeRequest, EmployeResponse } from '../employees.service';
import { GroupsService } from '../groups.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-employe',
  templateUrl: './employe.component.html',
  styleUrls: ['./employe.component.scss']
})
export class EmployeComponent implements OnInit {
  employees: EmployeResponse[] = [];
  employee: EmployeResponse | null = null;
  showAlert: boolean = false;
  alertMessage: string = '';
  newEmployee: EmployeRequest = {
    nomEmploye: '',
    codeEmployeSuperieur: undefined,
    codesGroupes: []
  };
  editingEmployee: EmployeRequest = {
    nomEmploye: '',
    codeEmployeSuperieur: undefined,
    codesGroupes: []
  };
  editingEmployeeId: number | null = null;
  groups: { codeGroupe: number; nomGroupe: string }[] = [];

  constructor(
    private employeesService: EmployeesService,
    private groupsService: GroupsService
  ) {}

  ngOnInit(): void {
    this.getAllEmployees();
    this.getAllGroups();
  }

  getAllGroups() {
    this.groupsService.getGroupes().subscribe(
      (res: any) => {
        this.groups = res;
      },
      (error: any) => {
        console.error("Error fetching groups:", error);
        this.showAlertMessage('Erreur lors de la récupération des groupes', false);
      }
    );
  }

  addEmployee() {
    this.employeesService.saveEmploye(this.newEmployee).subscribe(
      (res: EmployeResponse) => {
        this.getAllEmployees();
        this.newEmployee = { nomEmploye: '', codeEmployeSuperieur: undefined, codesGroupes: [] };
        Swal.fire({
          title: "Succès",
          text: "Ajout effectué avec succès!",
          icon: "success"
        });
        },
      (error: any) => {
        console.error("Error adding employee:", error);
        Swal.fire({
          title: "Erreur",
          text: "Erreur lors d'ajout d'employé!",
          icon: "error"
        });
      }
    );
  }

  getOneEmployee(codeEmploye: number) {
    this.employeesService.getEmploye(codeEmploye).subscribe(
      (res: EmployeResponse) => {
        this.employee = res;
      },
      (error: any) => {
        console.error("Error fetching employee:", error);
        this.showAlertMessage('Erreur lors de la récupération de l\'employé', false);
      }
    );
  }

  getAllEmployees() {
    this.employeesService.listEmployes().subscribe(
      (res: EmployeResponse[]) => {
        this.employees = res;
      },
      (error: any) => {
        console.error("Error fetching employees:", error);
        this.showAlertMessage('Erreur lors de la récupération des employés', false);
      }
    );
  }

  deleteEmployee(codeEmploye: number) {
    this.employeesService.deleteEmploye(codeEmploye).subscribe(
      () => {
        this.getAllEmployees();
        Swal.fire({
          title: "Succès",
          text: "Suppression effectué avec succès!",
          icon: "success"
        });
        },
      (error: any) => {
        console.error("Error deleting employee:", error);
        Swal.fire({
          title: "Erreur",
          text: "Erreur lors de suppression d'employé!",
          icon: "error"
        });
      }
    );
  }

  prepareEditEmployee(employee: EmployeResponse) {
    this.editingEmployeeId = employee.codeEmploye;
    this.editingEmployee = {
      nomEmploye: employee.nomEmploye,
      codeEmployeSuperieur: employee.codeEmployeSuperieur,
      codesGroupes: employee.codesGroupes || []
    };
  }

  updateEmployee() {
    if (this.editingEmployeeId !== null) {
      this.employeesService.updateEmploye(this.editingEmployeeId, this.editingEmployee).subscribe(
        (res: EmployeResponse) => {
          Swal.fire({
            title: "Succès",
            text: "Modification effectué avec succès!",
            icon: "success"
          });

          this.getAllEmployees();
          this.editingEmployeeId = null;

        },
        (error: any) => {
          console.error("Error updating employee:", error);
          Swal.fire({
            title: "Erreur",
            text: "Erreur lors de modification d'employé!",
            icon: "error"
          });
        }
      );
    }
  }

  onGroupSelectionChange(event: Event, employee: EmployeRequest) {
    const select = event.target as HTMLSelectElement;
    const selectedOptions = Array.from(select.selectedOptions).map(option => parseInt(option.value));
    employee.codesGroupes = selectedOptions;
  }

  showAlertMessage(message: string, isSuccess: boolean = true) {
    this.alertMessage = message;
    this.showAlert = true;
    setTimeout(() => {
      this.showAlert = false;
    }, 3000);
  }
}
