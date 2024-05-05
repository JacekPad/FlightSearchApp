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


  constructor(private flightService: FlightService) {}

  ngOnInit(): void {
    this.flightRoutes$ = this.getFlights();  
  }

  getFlights(): Observable<FlightRoute[]> {
    return this.flightService.getObservable();
  }

  test() {
    this.getFlights().subscribe(resutl => {
      console.log(resutl);
    })
  }
}
