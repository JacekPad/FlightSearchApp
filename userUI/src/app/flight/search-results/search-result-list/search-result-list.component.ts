import { Component, Input, OnInit } from '@angular/core';
import { FlightService } from '../../flight.service';
import { Observable } from 'rxjs';
import { FlightRoute } from '../../model/flight-routes';
import { tick } from '@angular/core/testing';
import { IFlightRouteResponse } from '../../model/flight-route-response';

@Component({
  selector: 'app-search-result-list',
  templateUrl: './search-result-list.component.html',
  styleUrl: './search-result-list.component.scss'
})
export class SearchResultListComponent implements OnInit {
  @Input()
  flightRoutes!: IFlightRouteResponse;
  
  constructor(private flightService: FlightService) {}

  ngOnInit(): void {

  }
}
