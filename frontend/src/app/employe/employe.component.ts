import { Component, OnInit } from '@angular/core';
import { EmployeesService, EmployeRequest, EmployeResponse } from '../employees.service';
import { GroupsService } from '../groups.service';
import { OperationResponse, OperationService } from '../operation.service';
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
  operations: OperationResponse[] = [];

  // Pagination variables
  currentPage: number = 1;
  itemsPerPage: number = 7;
  totalItems: number = 0;

  constructor(
    private employeesService: EmployeesService,
    private groupsService: GroupsService,
    private operationsService: OperationService
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
        Swal.fire({
          title: "Succée!",
          text: "Ajout effectué avec succès!",
          icon: "success"
        });
        this.getAllEmployees();
        this.newEmployee = { nomEmploye: '', codeEmployeSuperieur: undefined, codesGroupes: [] };
        this.showAlertMessage('Employé ajouté avec succès');
      },
      (error: any) => {
        Swal.fire({
          title: "Erreur!",
          text: "Erreur lors de l\'ajout de l\'employé!",
          icon: "error"
        });
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
        this.totalItems = this.employees.length;
      },
      (error: any) => {
        console.error("Error fetching employees:", error);
        this.showAlertMessage('Erreur lors de la récupération des employés', false);
      }
    );
  }

  openOperationsModal(codeEmploye: number) {
    this.operationsService.getOperationsByEmploye(codeEmploye).subscribe(
      (res: OperationResponse[]) => {
        this.operations = res;
        this.employee = this.employees.find(emp => emp.codeEmploye === codeEmploye) || null;
      },
      (error: any) => {
        console.error("Erreur lors de la récupération des opérations:", error);
        this.showAlertMessage('Erreur lors de la récupération des opérations', false);
      }
    );
  }

  deleteEmployee(codeEmploye: number) {
    this.employeesService.deleteEmploye(codeEmploye).subscribe(
      () => {
        Swal.fire({
          title: "Succée!",
          text: "Suppression effectué avec succès!",
          icon: "success"
        });
        this.getAllEmployees();
        this.showAlertMessage('Employé supprimé avec succès');
      },
      (error: any) => {
        Swal.fire({
          title: "Erreur!",
          text: "Erreur lors de la suppression de l\'employé!",
          icon: "error"
        });
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
          Swal.fire({
            title: "Succée!",
            text: "Modification effectué avec succès!",
            icon: "success"
          });
          this.showAlertMessage('Employé mis à jour avec succès');
        },
        (error: any) => {
          console.error("Error updating employee:", error);
          Swal.fire({
            title: "Erreur!",
            text: "Erreur lors de modification d'employé!",
            icon: "error"
          });
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

  // Pagination methods
  onPageChange(page: number) {
    this.currentPage = page;
  }

  get paginatedEmployees() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.employees.slice(startIndex, startIndex + this.itemsPerPage);
  }

  get totalPages() {
    return Math.ceil(this.totalItems / this.itemsPerPage);
  }

  get pageNumbers(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }
}
