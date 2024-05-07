import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookingRoutingModule } from './booking-routing.module';
import { BookingFlightComponent } from './booking-flight/booking-flight.component';
import { MaterialImportsModule } from '../shared/material-imports/material-imports.module';
import { FlightModule } from '../flight/flight.module';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    BookingFlightComponent
  ],
  imports: [
    CommonModule,
    BookingRoutingModule,
    MaterialImportsModule,
    FlightModule,
    ReactiveFormsModule
  ]
})
export class BookingModule { }
