import { Component, Input, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { Product } from '../../../models/product.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatSlideToggleModule, MatSlideToggleChange } from '@angular/material/slide-toggle';
import { ProductStatus } from '../../../models/product-status';
import { CustomDatePipe } from '../../../core/pipes/custom-date.pipe';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../../services/product.service';
import { MatDialog } from '@angular/material/dialog';
import { ProductEditComponent } from '../product-edit/product-edit.component';
@Component({
  selector: 'app-product-table',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatIconModule, CustomDatePipe, FormsModule, MatSlideToggleModule],
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
export class ProductTableComponent implements OnChanges {
  @Input() products: Product[] = [];
  public ProductStatus = ProductStatus;

  constructor(
    private productService: ProductService,
    private dialog: MatDialog
  ) {}

  dataSource = new MatTableDataSource<Product>(this.products);

  columnsToDisplay = ['id', 'name', 'price', 'status', 'createdTime', 'updatedTime', 'actions'];
  columnsToDisplayWithExpand = [...this.columnsToDisplay];
  expandedElement: Product | null = null;

  ngOnChanges() {
    this.dataSource.data = this.products;
  }

  editProduct(product: Product): void {
    const dialogRef = this.dialog.open(ProductEditComponent, {
      width: '600px',
      data: product
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.dataSource.data = this.dataSource.data.map(p => p.id === result.id ? result : p);
      }
    });
  }

  deleteProduct(product: Product): void {
    if (confirm('Are you sure you want to delete this product?')) {
      this.productService.deleteProduct(product.id).subscribe(() => {
        this.dataSource.data = this.dataSource.data.filter(p => p.id !== product.id);
      });
    }
  }

  toggleStatus(product: Product, checked: boolean): void {
    const newStatus = checked ? ProductStatus.ACTIVE : ProductStatus.INACTIVE;
    this.productService.updateProductStatus(product.id, newStatus).subscribe(() => {
      product.status = newStatus;
    });
  }
}