import { Injectable } from '@angular/core';
import { FlightDetails } from './model/flight-details';
import { FlightRoute } from './model/flight-routes';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  constructor() { }

  getObservable(): Observable<FlightRoute[]> {
    return of(this.createFlightRoute());
  }

  createFlight(): FlightDetails[] {
    const flightDetailsArr: FlightDetails[] = [];

    let flightDetails = new FlightDetails;
    flightDetails.id = 1;
    flightDetails.departureAirport = 'ABCD'
    flightDetails.arrivalAirpot = 'EFGH'
    flightDetails.price = 10000
    flightDetails.airline = 'testAirlines'
    flightDetails.airplaneType = 'AC130'
    flightDetails.seatsLeft = 6
    flightDetails.departureDate = new Date();
    flightDetails.arrivalDate = new Date();
    flightDetails.duration = 4200

    let flightDetails2 = new FlightDetails;
    flightDetails2.id = 2;
    flightDetails2.departureAirport = 'EFGH'
    flightDetails2.arrivalAirpot = 'QWER'
    flightDetails2.price = 10000
    flightDetails2.airline = 'testAirlines'
    flightDetails2.airplaneType = 'AC130'
    flightDetails2.seatsLeft = 6
    flightDetails2.departureDate = new Date();
    flightDetails2.arrivalDate = new Date();
    flightDetails2.duration = 4200

    let flightDetails3 = new FlightDetails;
    flightDetails3.id = 3;
    flightDetails3.departureAirport = 'QWER'
    flightDetails3.arrivalAirpot = 'ZZZZ'
    flightDetails3.price = 10000
    flightDetails3.airline = 'testAirlines'
    flightDetails3.airplaneType = 'AC130'
    flightDetails3.seatsLeft = 6
    flightDetails3.departureDate = new Date();
    flightDetails3.arrivalDate = new Date();
    flightDetails3.duration = 4200    

    flightDetailsArr.push(flightDetails);
    flightDetailsArr.push(flightDetails2);
    flightDetailsArr.push(flightDetails3);
    
    return flightDetailsArr;
  }

  createFlightRoute(): FlightRoute[] {
    const flightRoute = new FlightRoute
    flightRoute.flights = this.createFlight();
    flightRoute.departureAirport = flightRoute.flights[0].departureAirport;
    flightRoute.arrivalAirport = flightRoute.flights[2].arrivalAirpot;
    flightRoute.seatsLeft = 6;
    flightRoute.totalPrice = 10000*3;
    flightRoute.departureDate = new Date();
    flightRoute.arrivalDate = new Date();
    flightRoute.duration = 4200 * 3;
    flightRoute.numOfStops = 2;
    const flightRouteList: FlightRoute[] = [];
    flightRouteList.push(flightRoute);
    flightRouteList.push(flightRoute);
    flightRouteList.push(flightRoute);
    flightRouteList.push(flightRoute);
    return flightRouteList
  }




}
