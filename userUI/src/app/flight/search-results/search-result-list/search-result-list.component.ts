import { Component, Input, OnInit } from '@angular/core';
import { FlightService } from '../../flight.service';
import { FlightRoute } from '../../model/flight-routes';
import { IFlightRouteResponse } from '../../model/flight-route-response';
import { BookingService } from '../../../booking/booking.service';
import { FlightBookingChoice } from '../../model/flight-booking-choice';
import { FlightBooking } from '../../../booking/model/flight-booking-model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-result-list',
  templateUrl: './search-result-list.component.html',
  styleUrl: './search-result-list.component.scss'
})
export class SearchResultListComponent implements OnInit {
  @Input()
  flightRoutes!: IFlightRouteResponse;
  currentFlights: FlightRoute[] = [];
  lastChoice: boolean = false;
  flightChoice: string = 'departure';
  flightBooking: FlightBooking = new FlightBooking;

  constructor(private flightService: FlightService, private bookingService: BookingService,
    private router:Router
  ) { }

  ngOnInit(): void {
    this.currentFlights = this.flightRoutes.routeDeparture
    if (this.flightRoutes.routeReturn === null || this.flightRoutes.routeReturn.length === 0) {
      this.lastChoice = true;
    }
    console.log("test");
    console.log(this.lastChoice);

  }


  onRouteChoose(bookingChoice: FlightBookingChoice) {
    if (this.flightChoice === 'departure') {
      this.flightBooking.departureFlight = bookingChoice;
    } else if (this.flightChoice === 'return') {
      this.flightBooking.returnFlight = bookingChoice;
    }

    if (this.lastChoice) {
      this.bookingService.bookingChoice = this.flightBooking;
      this.router.navigate(['/booking']);
    }
    if (this.flightRoutes.routeReturn != null) {
      this.flightChoice = 'return';
      this.currentFlights = this.flightRoutes.routeReturn;
      this.lastChoice = true;
    }

  }
}
