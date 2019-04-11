import { Component, OnInit, OnDestroy } from '@angular/core';
import { OfferService } from '../offer.service';
import { Subscriber } from 'rxjs';

@Component({
  selector: 'app-offer-list',
  templateUrl: './offer-list.component.html',
  styleUrls: ['./offer-list.component.css']
})
export class OfferListComponent implements OnInit, OnDestroy {

  public offers: Array<any>;
  public subscriberGetOffers;

  constructor(private offerService: OfferService) { }

  ngOnInit() {
    this.subscriberGetOffers = this.offerService.getOffers().subscribe(data => {
      this.offers = data;
    });
  }

  ngOnDestroy(): void {
    this.subscriberGetOffers.unsubscribe();
  }

  public editOffer(offer) {
    // TODO
  }

  public deleteOffer(offer) {
    // TODO
  }

  public gotoHome() {
    // TODO
  }

  public addOffer() {
    // TODO
  }

}
