import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Product } from '../../../models/product.model';
import { ProductStatus } from '../../../models/product-status';
import { ProductService } from '../../../services/product.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-product-add',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatFormFieldModule, MatInputModule, MatSelectModule, ReactiveFormsModule],
  templateUrl: './product-add.component.html',
  styleUrl: './product-add.component.css'
})
export class ProductAddComponent {
  productForm: FormGroup;
  public ProductStatus = ProductStatus;
  lastProductId: number = 0;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private dialogRef: MatDialogRef<ProductAddComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Product
  ) {
    this.productForm = this.fb.group({
      id: [{ value: '', disabled: true }],
      name: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      status: [ProductStatus.ACTIVE, Validators.required],
      createdTime: [{ value: new Date().toISOString(), disabled: true }],
      updatedTime: [{ value: '', disabled: true }]
    });
  }

  ngOnInit(): void {
    this.getLastProductId();
  }

  getLastProductId(): void {
    this.productService.getProducts().subscribe({
      next: (products) => {
        if (products.length > 0) {
          this.lastProductId = Math.max(...products.map(p => p.id));
        }
        this.productForm.patchValue({ id: this.lastProductId + 1 });
      },
      error: (err) => alert('Error fetching products: ' + err)
    });
  }

  saveProduct(): void {
    if (this.productForm.valid) {
      const newProduct: Product = {
        id: this.lastProductId + 1,
        ...this.productForm.getRawValue(),
        createdTime: new Date().toISOString(),
        updatedTime: new Date().toISOString()
      };

      this.productService.addProduct(newProduct).subscribe({
        next: (addedProduct) => {
          this.dialogRef.close(addedProduct);
        },
        error: (err) => alert('Error adding product: ' + err)
      });
    }
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
