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
import { DatePipe } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  constructor(private http: HttpClient, private datePipe: DatePipe) { }


  getFlights(paramsObj: ISearchParams): Observable<IFlightRouteResponse> {
    let departureDate = this.datePipe.transform(paramsObj.departureDate,'yyyy-MM-ddTHH:mm:ss')
    let returnDate = this.datePipe.transform(paramsObj.returnDate,'yyyy-MM-ddTHH:mm:ss')
    let paramMap = new HttpParams()
    .set('flightClass', paramsObj.flightClass)
    .set('flightType', paramsObj.flightType)
    .set('departureAirportIata', paramsObj.departureAirport)
    .set('arrivalAirportIata', paramsObj.arrivalAirport)
    .set('maxStops', paramsObj.maxStops)
    .set('adult', paramsObj.adultNumber)
    .set('child', paramsObj.childNumber)
    .set('infant', paramsObj.infantNumber);

    if (departureDate != null) {
      paramMap = paramMap.set('departureDate', departureDate.toString())
    }
    if (returnDate != null) {
      paramMap = paramMap.set('returnDate', returnDate.toString())
    }
    const options = { params: paramMap}
    return this.http.get<IFlightRouteResponse>("/api/v1/routes", options);
  }


}
