import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookingRoutingModule } from './booking-routing.module';
import { BookingFlightComponent } from './booking-flight/booking-flight.component';
import { MaterialImportsModule } from '../shared/material-imports/material-imports.module';
import { FlightModule } from '../flight/flight.module';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SuccessComponent } from './checkout/success/success.component';
import { CancelComponent } from './checkout/cancel/cancel.component';


@NgModule({
  declarations: [
    BookingFlightComponent,
    SuccessComponent,
    CancelComponent
  ],
  imports: [
    CommonModule,
    BookingRoutingModule,
    MaterialImportsModule,
    FlightModule,
    ReactiveFormsModule,
    HttpClientModule
  ]
})
export class BookingModule { }
