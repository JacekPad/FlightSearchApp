import { Injectable } from '@angular/core';
import { FlightDetails } from './model/flight-details';
import { FlightRoute } from './model/flight-routes';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { FlightPricingOption } from './model/flight-pricing-option';
import { FlightClassTypes } from './model/enums/flight-class-types';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ISearchParams } from './model/search-params';
import { IFLightRoutePassanger } from './model/flight-route-passanger';
import { IFlightRouteResponse } from './model/flight-route-response';

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  constructor(private http: HttpClient) { }

  flights$: Observable<IFlightRouteResponse> | undefined;

  flight?: FlightRoute[]

  getObservable(): Observable<IFlightRouteResponse> | undefined {
    return this.flights$;
  }


  getFlights(paramsObj: ISearchParams): Observable<IFlightRouteResponse> {
    console.log("obs function go");
    const options = { params: new HttpParams()
    .set('flightClass', paramsObj.flightClass)
    .set('departureAirportIata', paramsObj.departureAirport)
    .set('arrivalAirportIata', paramsObj.arrivalAirport)
    .set('maxStops', paramsObj.maxStops)
    .set('adult', paramsObj.adultNumber)
    .set('child', paramsObj.childNumber)
    .set('infant', paramsObj.infantNumber)}
    return this.flights$ = this.http.get<IFlightRouteResponse>("/app/routes", options);
  }
}
