import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';

import { CampaignService } from '../campaign.service';
import { Campaign } from '../campaign.model';
import { ProductService } from '../../products/product.service';

@Component({
  selector: 'app-campaign-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './campaign-list.component.html',
  styleUrls: ['./campaign-list.component.css']
})
export class CampaignListComponent implements OnInit {

  productId!: number;
  productName = '';

  campaigns: Campaign[] = [];

  name = '';
  keywords = '';
  bidAmount = 0;
  fund = 0;
  status: 'ON' | 'OFF' = 'ON';
  town = '';
  radiusKm = 0;

  constructor(
    private route: ActivatedRoute,
    private campaignService: CampaignService,
    private productService: ProductService
  ) {}

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('productId'));

    this.productService.getOne(this.productId).subscribe({
      next: p => this.productName = p.name
    });

    this.load();
  }

  load() {
    this.campaignService.getAll(this.productId).subscribe({
      next: data => this.campaigns = data,
      error: err => console.error(err)
    });
  }

  addCampaign() {
    const payload = {
      name: this.name,
      keywords: this.keywords.split(',').map(k => k.trim()),
      bidAmount: this.bidAmount,
      fund: this.fund,
      status: this.status,
      town: this.town,
      radiusKm: this.radiusKm
    };

    this.campaignService.create(this.productId, payload).subscribe({
      next: () => {
        this.resetForm();
        this.load();
      }
    });
  }

  deleteCampaign(campaignId: number) {
    if (!confirm('Delete this campaign?')) return;

    this.campaignService.delete(this.productId, campaignId).subscribe({
      next: () => this.load()
    });
  }

  resetForm() {
    this.name = '';
    this.keywords = '';
    this.bidAmount = 0;
    this.fund = 0;
    this.status = 'ON';
    this.town = '';
    this.radiusKm = 0;
  }
}
