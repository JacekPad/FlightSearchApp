import { Component, Input } from '@angular/core';
import { FlightRoute } from '../model/flight-routes';

@Component({
  selector: 'app-flight-details',
  templateUrl: './flight-details.component.html',
  styleUrl: './flight-details.component.scss'
})
export class FlightDetailsComponent {

  @Input()
  flight!: FlightRoute | null


}
