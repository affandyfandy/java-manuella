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
  selector: 'app-product-edit',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatFormFieldModule, MatInputModule, MatSelectModule, ReactiveFormsModule],
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {
  productForm: FormGroup;
  public ProductStatus = ProductStatus;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private dialogRef: MatDialogRef<ProductEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Product
  ) {
    this.productForm = this.fb.group({
      id: [{ value: '', disabled: true }],
      name: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      status: ['', Validators.required],
      createdTime: [{ value: '', disabled: true }],
      updatedTime: [{ value: '', disabled: true }]
    });
  }

  ngOnInit(): void {
    if (this.data) {
      this.productForm.patchValue(this.data);
    }
  }

  saveProduct(): void {
    if (this.productForm.valid) {
      const updatedProduct: Product = {
        ...this.data,
        ...this.productForm.getRawValue(),
        updatedTime: new Date().toISOString()
      };

      this.productService.updateProduct(updatedProduct).subscribe({
        next: () => {
          this.dialogRef.close(updatedProduct);
        },
        error: (err) => alert('Error updating product: ' + err)
      });
    }
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}