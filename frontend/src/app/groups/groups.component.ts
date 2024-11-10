import {Component, OnInit} from '@angular/core';
import {EmployeesService} from '../employees.service';
import {Router} from '@angular/router';
import {GroupsService} from '../groups.service';

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
      this.groupsService.add(this.group).subscribe((res) => {
        this.getAllGroup();
      })
    }

  }

  updateGroup() {
    this.groupsService.update(this.newGroup.codeGroupe, this.newGroup).subscribe((res) => {

      this.getAllGroup();

    });
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
    this.groupsService.delete(codeGroup).subscribe((res: any) => {
      this.groups = res;
      this.getAllGroup();  // Reload the employee list after deletion
    });
  }

}
