import { Component, OnInit } from '@angular/core';
import { EmployeesService, EmployeRequest, EmployeResponse } from '../employees.service';
import { GroupsService } from '../groups.service';

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
        this.showAlertMessage('Employé ajouté avec succès');
      },
      (error: any) => {
        console.error("Error adding employee:", error);
        this.showAlertMessage('Erreur lors de l\'ajout de l\'employé', false);
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
        this.showAlertMessage('Employé supprimé avec succès');
      },
      (error: any) => {
        console.error("Error deleting employee:", error);
        this.showAlertMessage('Erreur lors de la suppression de l\'employé', false);
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
          this.getAllEmployees();
          this.editingEmployeeId = null;
          this.showAlertMessage('Employé mis à jour avec succès');
        },
        (error: any) => {
          console.error("Error updating employee:", error);
          this.showAlertMessage('Erreur lors de la mise à jour de l\'employé', false);
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
