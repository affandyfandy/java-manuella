import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchBarComponent } from '../../main/components/search-bar/search-bar.component';
import { ProductTableComponent } from '../../main/components/product-table/product-table.component';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product.model';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, SearchBarComponent, ProductTableComponent],
  providers: [ProductService],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit{
  products: Product[] = [];
  filteredProducts: Product[] = [];
  currentFilter: string = '';
  currentSort: { field: string, order: string } = { field: 'name', order: 'asc' };

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
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

  onSearch(): void {
    this.applyFilter();
  }
}
