import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FlightRoutingModule } from './flight-routing.module';
import { FlightSearchComponent } from './flight-search/flight-search.component';
import { FlightDetailsComponent } from './flight-details/flight-details.component';
import { MaterialImportsModule } from '../shared/material-imports/material-imports.module';
import { PassangerOptionsComponent } from './passanger-options/passanger-options.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PassangerCountComponent } from './passanger-count/passanger-count.component';
import { SearchResultListComponent } from './search-results/search-result-list/search-result-list.component';
import { SearchResultCardComponent } from './search-results/search-result-card/search-result-card.component';
import { BookingOptionsComponent } from './search-results/booking-options/booking-options.component';
import { FlightRouteDetailsComponent } from './flight-route-details/flight-route-details.component';


@NgModule({
  declarations: [
    FlightSearchComponent,
    FlightDetailsComponent,
    PassangerOptionsComponent,
    PassangerCountComponent,
    SearchResultListComponent,
    SearchResultCardComponent,
    BookingOptionsComponent,
    FlightRouteDetailsComponent
  ],
  imports: [
    CommonModule,
    FlightRoutingModule,
    MaterialImportsModule,
    ReactiveFormsModule
  ]
})
export class FlightModule { }
