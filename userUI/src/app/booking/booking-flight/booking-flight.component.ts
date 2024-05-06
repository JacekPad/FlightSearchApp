import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { FlightRoute } from '../../flight/model/flight-routes';

@Component({
  selector: 'app-booking-flight',
  templateUrl: './booking-flight.component.html',
  styleUrl: './booking-flight.component.scss'
})
export class BookingFlightComponent implements OnInit {

  flightRouteId!: number;

  constructor(private route: ActivatedRoute) {}


  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      // get flightRoute param from ID and start booking process
      this.flightRouteId = params['id'];
    });
  }

}
