import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FlightRoute } from '../../model/flight-routes';
import { MatDialog } from '@angular/material/dialog';
import { FlightDetailsComponent } from '../../flight-details/flight-details.component';
import { KeyValue } from '@angular/common';
import { FlightBookingChoice } from '../../model/flight-booking-choice';

@Component({
  selector: 'app-search-result-card',
  templateUrl: './search-result-card.component.html',
  styleUrl: './search-result-card.component.scss'
})
export class SearchResultCardComponent {

  @Input()
  flightRoute!: FlightRoute

  @Input()
  routeId: string = '';

  @Output()
  bookingChoiceEmitter: EventEmitter<FlightBookingChoice> = new EventEmitter<FlightBookingChoice>();

  constructor(private dialog: MatDialog) {}

  getFlightDetails(flightRoute: FlightRoute) {
    console.log('click works - flights');
    const flightDetailDialog = this.dialog.open(FlightDetailsComponent, {
      data: { flightRoute: flightRoute},
      width: '60%'
    });
    flightDetailDialog.afterClosed()
  }

  chooseFlightOption(pricing: KeyValue<string, number>) {
    const bookingChoice: FlightBookingChoice =  new FlightBookingChoice;
    bookingChoice.flightClass = pricing.key;
    bookingChoice.ticketPrice = pricing.value;
    bookingChoice.routeId = this.routeId
    bookingChoice.flightId = this.flightRoute.id;
    this.bookingChoiceEmitter.emit(bookingChoice);
  }
}
