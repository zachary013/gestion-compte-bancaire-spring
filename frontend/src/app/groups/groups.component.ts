import {Component, OnInit} from '@angular/core';
import {EmployeesService} from '../employees.service';
import {Router} from '@angular/router';
import {GroupsService} from '../groups.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrl: './groups.component.scss'
})
export class GroupsComponent implements OnInit{
  constructor(private groupsService: GroupsService, private router:Router) {}

  groups : any ;
  group : any = {
    "codeGroupe" : -1,
    "nomGroupe": "",
    "employes": []

  };
  newGroup : any = {
    "nomGroupe": "",
    "employes": []
  };

  ngOnInit(): void {
    this.getAllGroup(); // Corrected to use 'this'
  }

  addGroup() {
    if(this.group.nomGroupe != ""){
      this.groupsService.add(this.group).subscribe({
        next: (res) => {
          Swal.fire({
            title: "Succès",
            text: "Ajout effectué avec succès!",
            icon: "success"
          });
          this.getAllGroup();
        },
        error: (err) => {
          console.error('Erreur lors d\'ajout de group:', err)
          Swal.fire({
            title: "Erreur",
            text: "Erreur lors d'ajout de group!",
            icon: "error"
          });
        }
        }
      )
    }

  }

  updateGroup() {
    this.groupsService.update(this.newGroup.codeGroupe, this.newGroup).subscribe({
      next: (res) => {
        Swal.fire({
          title: "Succès",
          text: "Modification effectué avec succès!",
          icon: "success"
        });
        this.getAllGroup();

      },
      error: (err) => {
        console.error('Erreur lors de modifier de group:', err)
        Swal.fire({
          title: "Erreur",
          text: "Erreur lors de modifier de group!",
          icon: "error"
        });
      }
      }
    );
  }


  setUpdateGroup(group: any) {
    this.newGroup = { ...group }; // Copy selected group data to newGroup
  }


  getAllGroup() {
    this.groupsService.getGroupes().subscribe((res: any) => {
      console.log("API response:", res); // Add this to see the structure of `res`
      this.groups = res;
    });
  }

  deleteGroup(codeGroup: number){
    this.groupsService.delete(codeGroup).subscribe({
      next: (res: any) => {
        this.groups = res;
        Swal.fire({
          title: "Succès",
          text: "Suppression effectué avec succès!",
          icon: "success"
        });
        this.getAllGroup();  // Reload the employee list after deletion
      },
      error: err => {
        Swal.fire({
          title: "Erreur",
          text: "Erreur lors de suppression de group!",
          icon: "error"
        });
      }
    });
  }

}
