import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SearchBarComponent } from '../../main/components/search-bar/search-bar.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterOutlet, SearchBarComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  currentFilter: string = '';
  currentSort: { field: string, order: string } = { field: 'name', order: 'asc' };

  constructor(private router: Router) {}

  onSearch(event: { query: string; sort: { field: string; order: string } }): void {
    const { query, sort } = event;
    this.router.navigate(['/product'], {
      queryParams: {
        search: query,
        sortField: sort.field,
        sortOrder: sort.order
      }
    });
  }
}
