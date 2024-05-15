import { Component, Input } from '@angular/core';
import { FlightPricingOption, IFlightPricingOption } from '../../model/flight-pricing-option';

@Component({
  selector: 'app-booking-options',
  templateUrl: './booking-options.component.html',
  styleUrl: './booking-options.component.scss'
})
export class BookingOptionsComponent {

  @Input()
  prices!: number;

  @Input()
  type!: string


  calculatePrice(): string {
    let formatedPrice = this.prices / 100;
    return formatedPrice.toString();
  }
}
