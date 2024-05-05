import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FlightRoute } from '../../model/flight-routes';

@Component({
  selector: 'app-search-result-card',
  templateUrl: './search-result-card.component.html',
  styleUrl: './search-result-card.component.scss'
})
export class SearchResultCardComponent {

  @Input()
  flightRoute!: FlightRoute

  @Output()
  flightDetailsEmitter: EventEmitter<FlightRoute> = new EventEmitter();

  constructor() {}


  calculateDuration(): string {
    let duration = this.flightRoute.duration;
    let formatedDuration = duration / 60;
    if (duration < 60) {
      return formatedDuration.toString().concat(' Minutes')
    } else {
      return formatedDuration.toString().concat(' Hours')
    }
  }

  getFlightDetails() {
    console.log('click works - flights');
    this.flightDetailsEmitter.emit(this.flightRoute);
  }

  getPricingOptions(event: string) {
    console.log('mioght be router and not click event [different page]');
    console.log(event);
    
  }
}
