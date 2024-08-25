import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { SearchBarComponent } from '../../main/components/search-bar/search-bar.component';
import { ProductTableComponent } from '../../main/components/product-table/product-table.component';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';
import { ProductAddComponent } from '../../main/components/product-add/product-add.component';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, SearchBarComponent, ProductTableComponent, MatDialogModule, MatButtonModule],
  providers: [ProductService],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  currentFilter: string = '';
  currentSort: { field: string, order: string } = { field: 'name', order: 'asc' };

  constructor(
    private productService: ProductService,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadProducts();
    this.route.queryParams.subscribe(params => {
      const query = params['search'] || '';
      const sortField = params['sortField'] || 'name';
      const sortOrder = params['sortOrder'] || 'asc';

      this.currentFilter = query;
      this.currentSort = { field: sortField, order: sortOrder };

      this.applyFilter(this.currentFilter);
    });
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe(products => {
      this.products = products;
      this.applyFilter();
    });
  }
  
  applyFilter(filterValue: string = this.currentFilter): void {
    this.currentFilter = filterValue.trim().toLowerCase();
    this.productService.filterProducts(this.currentFilter).subscribe(filtered => {
      this.filteredProducts = this.sortProducts(filtered);
    });
  }

  sortData(sort: { field: string, order: string }): void {
    this.currentSort = sort;
    this.filteredProducts = this.sortProducts(this.filteredProducts);
  }

  sortProducts(products: Product[]): Product[] {
    const { field, order } = this.currentSort;
    return products.sort((a, b) => {
      const aValue = a[field as keyof Product];
      const bValue = b[field as keyof Product];
      if (aValue < bValue) return order === 'asc' ? -1 : 1;
      if (aValue > bValue) return order === 'asc' ? 1 : -1;
      return 0;
    });
  }

  onSearch(searchData: { query: string; sort: { field: string; order: string } }): void {
    this.currentFilter = searchData.query;
    this.currentSort = searchData.sort;
    this.applyFilter();
  }

  onAddProduct(): void {
    const dialogRef = this.dialog.open(ProductAddComponent, {
      width: '400px',
      data: { id: 0, name: '', price: 0, status: 'ACTIVE', createdTime: new Date(), updatedTime: new Date() }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadProducts();
      }
    });
  }
}