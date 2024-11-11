import { Component, OnInit } from '@angular/core';
import { OperationService, OperationResponse } from '../operation.service';

@Component({
  selector: 'app-operation',
  templateUrl: './operation.component.html',
  styleUrls: ['./operation.component.scss']
})
export class OperationComponent implements OnInit {
  operations: OperationResponse[] = [];
  currentPage = 1;
  pageSize = 10;
  totalItems = 0;

  constructor(private operationService: OperationService) {}

  ngOnInit(): void {
    this.loadOperations();
  }

  loadOperations(): void {
    this.operationService.getAllOperations().subscribe({
      next: (res: OperationResponse[]) => {
        this.operations = res.sort((a, b) =>
          new Date(b.dateOperation).getTime() - new Date(a.dateOperation).getTime()
        );
        this.totalItems = this.operations.length;
      },
      error: (error) => console.error('Error fetching operations:', error)
    });
  }

  get paginatedOperations(): OperationResponse[] {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    return this.operations.slice(startIndex, startIndex + this.pageSize);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
  }

  protected readonly Math = Math;
}
