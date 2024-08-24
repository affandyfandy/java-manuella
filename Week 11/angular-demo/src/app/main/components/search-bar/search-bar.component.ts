import { Component, Output, EventEmitter } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatSelectModule, MatOptionModule, MatButtonModule],
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent {
  @Output() filterChange = new EventEmitter<string>();
  @Output() sortChange = new EventEmitter<{ field: string, order: string }>();
  @Output() search = new EventEmitter<void>();

  filterValue: string = '';
  sortField: string = 'name';
  sortOrder: string = 'asc';

  applyFilter(event: Event): void {
    this.filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
  }

  sortData(field: string): void {
    this.sortField = field;
    this.sortChange.emit({ field: this.sortField, order: this.sortOrder });
  }

  onOrderChange(order: string): void {
    this.sortOrder = order;
    this.sortChange.emit({ field: this.sortField, order: this.sortOrder });
  }

  onSearch(): void {
    this.search.emit();
    this.filterChange.emit(this.filterValue);
    this.sortChange.emit({ field: this.sortField, order: this.sortOrder });
  }
}