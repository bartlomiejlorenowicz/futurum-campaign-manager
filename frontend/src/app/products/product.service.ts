import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from './product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl);
  }

  create(product: { name: string; emeraldAccountId: number }): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product);
  }

delete(productId: number) {
  return this.http.delete(
    `http://localhost:8080/api/products/${productId}`
  );
}

update(productId: number, payload: { name: string }) {
  return this.http.put<Product>(
    `http://localhost:8080/api/products/${productId}`,
    payload
  );
}

getOne(productId: number) {
  return this.http.get<Product>(
    `http://localhost:8080/api/products/${productId}`
  );
}

}
