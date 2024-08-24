import { Component, Input } from '@angular/core';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { Product } from '../../../models/product.model';
import { MatTableDataSource } from '@angular/material/table';
import { ProductStatus } from '../../../models/product-status';
import { CustomDatePipe } from '../../../core/pipes/custom-date.pipe';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-product-table',
  standalone: true,
  imports: [MatTableModule, MatButtonModule, MatIconModule, CustomDatePipe, FormsModule],
  templateUrl: './product-table.component.html',
  styleUrls: ['./product-table.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed,void', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ProductTableComponent {
  @Input() products: Product[] = [];

  dataSource = new MatTableDataSource<Product>(this.products);

  columnsToDisplay = ['id', 'name', 'price', 'status', 'createdTime', 'updatedTime', 'actions'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay];
  expandedElement: Product | null = null;
  
  ngOnChanges() {
    this.dataSource.data = this.products;
  }

  editProduct(product: Product): void {
    this.expandedElement = this.expandedElement === product ? null : product;
    console.log('Edit product:', product);
  }

  deleteProduct(product: Product): void {
    console.log('Delete product:', product);
  }
}