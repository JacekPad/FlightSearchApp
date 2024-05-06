import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FlightRoute } from '../../model/flight-routes';
import { MatDialog } from '@angular/material/dialog';
import { FlightDetailsComponent } from '../../flight-details/flight-details.component';

@Component({
  selector: 'app-search-result-card',
  templateUrl: './search-result-card.component.html',
  styleUrl: './search-result-card.component.scss'
})
export class SearchResultCardComponent {

  @Input()
  flightRoute!: FlightRoute

  constructor(private dialog: MatDialog) {}

  getFlightDetails(flightRoute: FlightRoute) {
    console.log('click works - flights');
    const flightDetailDialog = this.dialog.open(FlightDetailsComponent, {
      data: { flightRoute: flightRoute},
      width: '60%'
    });
    flightDetailDialog.afterClosed()
  }

  getPricingOptions(event: string) {
    console.log('mioght be router and not click event [different page]');
    console.log(event);
    
  }
}
