import { Injectable } from '@angular/core';
import { FlightSearchComponent } from '../flight/flight-search/flight-search.component';
import { FlightRoute, IFlightRoute } from '../flight/model/flight-routes';
import { Observable, of } from 'rxjs';
import { FlightPricingOption } from '../flight/model/flight-pricing-option';
import { FlightClassTypes } from '../flight/model/enums/flight-class-types';
import { FlightDetails } from '../flight/model/flight-details';
import { FlightRoutePassanger } from '../flight/model/flight-route-passanger';
import { PassangerCount } from '../flight/model/passanger-count-model';
import { PassangerType } from '../flight/model/enums/passanger-types';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  constructor() { }

  getBookingById(flightRouteId: number, flightClass: string): Observable<FlightRoute> {
    return of();
  }

}
