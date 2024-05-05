import { Component, Input } from '@angular/core';
import { FlightRoute } from '../../model/flight-routes';

@Component({
  selector: 'app-search-result-card',
  templateUrl: './search-result-card.component.html',
  styleUrl: './search-result-card.component.scss'
})
export class SearchResultCardComponent {


  @Input()
  flightRoute!: FlightRoute

  constructor() {}
}
