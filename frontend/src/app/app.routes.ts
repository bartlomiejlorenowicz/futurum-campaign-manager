import { Routes } from '@angular/router';
import { ProductListComponent } from './products/product-list/product-list.component';
import { ProductEditComponent } from './products/product-edit/product-edit.component';
import { CampaignListComponent } from './campaigns/campaign-list/campaign-list.component';
import { CampaignEditComponent } from './campaigns/campaign-edit/campaign-edit.component';

export const routes: Routes = [
  { path: '', component: ProductListComponent },
  {
      path: 'products/:productId/edit',
      component: ProductEditComponent
    },
  { path: 'products/:productId/campaigns', component: CampaignListComponent },
  {
      path: 'products/:productId/campaigns/:campaignId/edit',
      component: CampaignEditComponent
    }
];
