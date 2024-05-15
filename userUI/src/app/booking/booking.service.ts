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
import { IFlightRouteBooking } from '../flight/model/flight-route-booking-model';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  constructor(private http: HttpClient) { }

  getBookingById(flightRouteId: string, routeId: string, flightClass: string): Observable<IFlightRouteBooking> {
    const options = {params: new HttpParams()
      .set('routeId', routeId)
      .set('flightClass', flightClass)
    }
    return this.http.get<IFlightRouteBooking>(`/api/v1/option/${flightRouteId}/booking`, options);
  }
}
