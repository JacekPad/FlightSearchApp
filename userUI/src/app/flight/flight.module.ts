import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FlightRoutingModule } from './flight-routing.module';
import { FlightListComponent } from './flight-list/flight-list.component';
import { FlightSearchComponent } from './flight-search/flight-search.component';
import { FlightDetailsComponent } from './flight-details/flight-details.component';
import { MaterialImportsModule } from '../shared/material-imports/material-imports.module';
import { PassangerOptionsComponent } from './passanger-options/passanger-options.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PassangerCountComponent } from './passanger-count/passanger-count.component';
import { SearchResultListComponent } from './search-results/search-result-list/search-result-list.component';
import { SearchResultCardComponent } from './search-results/search-result-card/search-result-card.component';
import { BookingOptionsComponent } from './search-results/booking-options/booking-options.component';


@NgModule({
  declarations: [
    FlightListComponent,
    FlightSearchComponent,
    FlightDetailsComponent,
    PassangerOptionsComponent,
    PassangerCountComponent,
    SearchResultListComponent,
    SearchResultCardComponent,
    BookingOptionsComponent
  ],
  imports: [
    CommonModule,
    FlightRoutingModule,
    MaterialImportsModule,
    ReactiveFormsModule
  ]
})
export class FlightModule { }
