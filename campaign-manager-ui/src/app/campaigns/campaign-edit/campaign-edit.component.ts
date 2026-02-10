import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { CampaignService } from '../campaign.service';
import { Campaign } from '../campaign.model';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './campaign-edit.component.html',
  styleUrls: ['./campaign-edit.component.css']
})
export class CampaignEditComponent implements OnInit {

  productId!: number;
  campaignId!: number;

  name = '';
  keywords = '';
  bidAmount = 0;
  fund = 0;
  status: 'ON' | 'OFF' = 'ON';
  town = '';
  radiusKm = 0;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private campaignService: CampaignService
  ) {}

  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('productId'));
    this.campaignId = Number(this.route.snapshot.paramMap.get('campaignId'));

    this.campaignService
      .getOne(this.productId, this.campaignId)
      .subscribe((c: Campaign) => {
        this.name = c.name;
        this.keywords = c.keywords.join(', ');
        this.bidAmount = c.bidAmount;
        this.fund = c.fund;
        this.status = c.status;
        this.town = c.town;
        this.radiusKm = c.radiusKm;
      });
  }

  save(): void {
    const payload = {
      name: this.name,
      keywords: this.keywords.split(',').map(k => k.trim()),
      bidAmount: this.bidAmount,
      fund: this.fund,
      status: this.status,
      town: this.town,
      radiusKm: this.radiusKm
    };

    this.campaignService
      .update(this.productId, this.campaignId, payload)
      .subscribe(() => {
        this.router.navigate([
          '/products',
          this.productId,
          'campaigns'
        ]);
      });
  }
}
