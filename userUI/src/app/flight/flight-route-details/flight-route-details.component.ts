import { Component, Input } from '@angular/core';
import { FlightRoute } from '../model/flight-routes';

@Component({
  selector: 'app-flight-route-details',
  templateUrl: './flight-route-details.component.html',
  styleUrl: './flight-route-details.component.scss'
})
export class FlightRouteDetailsComponent {

    @Input()
    flightRoute!: FlightRoute;

    calculateDuration(): string {
      let duration = this.flightRoute.duration;
      let formatedDuration = (duration / 60).toFixed(0);
      if (duration < 60) {
        return formatedDuration.toString().concat(' Minutes')
      } else {
        return formatedDuration.toString().concat(' Hours')
      }
    }
}
