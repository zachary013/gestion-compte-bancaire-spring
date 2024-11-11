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
  constructor(
    private groupsService: GroupsService,
    private router:Router
  ) {}

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

  // Pagination variables
  currentPage: number = 1;
  itemsPerPage: number = 5;
  totalItems: number = 0;


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
      this.groups = res;
      this.totalItems = this.groups.length;
    });
  }

  deleteGroup(codeGroup: number){
    this.groupsService.delete(codeGroup).subscribe((res: any) => {
      this.groups = res;
      this.getAllGroup();
    });
  }

  // Pagination methods
  onPageChange(page: number) {
    this.currentPage = page;
  }

  get paginatedGroups() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    return this.groups.slice(startIndex, startIndex + this.itemsPerPage);
  }

  get totalPages() {
    return Math.ceil(this.totalItems / this.itemsPerPage);
  }

  get pageNumbers(): number[] {
    return Array(this.totalPages).fill(0).map((x, i) => i + 1);
  }

  protected readonly Math = Math;

}
