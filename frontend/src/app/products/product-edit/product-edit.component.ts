import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../product.service';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-product-edit',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './product-edit.component.html',
  styleUrls: ['./product-edit.component.css']
})
export class ProductEditComponent implements OnInit {

  productId!: number;
  name = '';
  campaignsCount = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('productId'));

    this.productService.getOne(this.productId).subscribe(p => {
      this.name = p.name;
      this.campaignsCount = p.campaignsCount;
    });
  }

  save() {
    this.productService.update(this.productId, {
      name: this.name
    }).subscribe(() => {
      this.router.navigate(['/']);
    });
  }
}
