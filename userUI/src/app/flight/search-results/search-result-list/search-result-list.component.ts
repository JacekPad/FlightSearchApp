import { Component, OnInit } from '@angular/core';
import { FlightService } from '../../flight.service';
import { Observable } from 'rxjs';
import { FlightRoute } from '../../model/flight-routes';

@Component({
  selector: 'app-search-result-list',
  templateUrl: './search-result-list.component.html',
  styleUrl: './search-result-list.component.scss'
})
export class SearchResultListComponent implements OnInit {

  flightRoutes$?: Observable<FlightRoute[]>;

  activeRouteDetails: FlightRoute | null = null;

  constructor(private flightService: FlightService) {}

  ngOnInit(): void {
    this.flightRoutes$ = this.getFlights();  
  }

  getFlights(): Observable<FlightRoute[]> {
    return this.flightService.getObservable();
  }

  openFlightDetails(flightRoute: FlightRoute) {
    console.log('this flight route is transmited');
    console.log(flightRoute);
    this.activeRouteDetails = flightRoute;
  }

  onCloseDetails() {
    this.activeRouteDetails = null;
    console.log(this.activeRouteDetails);
    
  }

}
