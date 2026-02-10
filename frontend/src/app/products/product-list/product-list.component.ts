import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../product.service';
import { Product } from '../product.model';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];

  name = '';
  emeraldAccountId!: number;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.load();
  }

  load() {
    this.productService.getAll().subscribe({
      next: data => this.products = data,
      error: err => console.error('API error', err)
    });
  }

  addProduct() {
    this.productService.create({
      name: this.name,
      emeraldAccountId: this.emeraldAccountId
    }).subscribe({
      next: () => {
        this.name = '';
        this.emeraldAccountId = 0;
        this.load();
      },
      error: err => console.error(err)
    });
  }

deleteProduct(productId: number) {
  if (!confirm('Delete product and all campaigns?')) {
    return;
  }

  this.productService.delete(productId).subscribe({
    next: () => this.load(),
    error: err => console.error('Delete failed', err)
  });
}

updateProduct(product: Product) {
  this.productService.update(product.id, {
    name: product.name
  }).subscribe({
    error: err => alert('Update failed')
  });
}


}
