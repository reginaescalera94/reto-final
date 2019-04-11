import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OfferListComponent } from './offer-list/offer-list.component';
import { OfferService } from './offer.service';

@NgModule({
  imports: [
    CommonModule
  ],
  providers: [OfferService],
  declarations: [OfferListComponent]
})
export class OffersModule { }
