import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Campaign } from './campaign.model';

@Injectable({
  providedIn: 'root'
})
export class CampaignService {

  private baseUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) {}

  getAll(productId: number): Observable<Campaign[]> {
    return this.http.get<Campaign[]>(
      `${this.baseUrl}/${productId}/campaigns`
    );
  }

  create(productId: number, campaign: any): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${productId}/campaigns`,
      campaign
    );
  }

  getOne(productId: number, campaignId: number) {
    return this.http.get<Campaign>(
      `/api/products/${productId}/campaigns/${campaignId}`
    );
  }

  update(productId: number, campaignId: number, campaign: any) {
    return this.http.put(
      `${this.baseUrl}/${productId}/campaigns/${campaignId}`,
      campaign
    );
  }

delete(productId: number, campaignId: number) {
    return this.http.delete(
      `${this.baseUrl}/${productId}/campaigns/${campaignId}`
    );
  }
}
