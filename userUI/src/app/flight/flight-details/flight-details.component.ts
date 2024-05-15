import { Component, Inject, Input, OnInit } from '@angular/core';
import { FlightRoute } from '../model/flight-routes';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FlightDetails } from '../model/flight-details';

@Component({
  selector: 'app-flight-details',
  templateUrl: './flight-details.component.html',
  styleUrl: './flight-details.component.scss'
})
export class FlightDetailsComponent implements OnInit {

  flight!: FlightRoute;

  constructor(@Inject(MAT_DIALOG_DATA) public data: {flightRoute: FlightRoute}) {}


  ngOnInit(): void {
    this.flight = this.data.flightRoute;
  }

  calculateDuration(arrivalDate: Date, departureDate: Date): string {
    let timeDifference = Math.abs(new Date(arrivalDate).getTime() - new Date(departureDate).getTime());
    let formatedDuration = (timeDifference / (60 * 60 * 1000)).toFixed(0);
    if (timeDifference < 60) {
      return formatedDuration.toString().concat(' Minutes')
    } else {
      return formatedDuration.toString().concat(' Hours')
    }
  }
}
