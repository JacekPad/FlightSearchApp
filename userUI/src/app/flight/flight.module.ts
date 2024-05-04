import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FlightRoutingModule } from './flight-routing.module';
import { FlightListComponent } from './flight-list/flight-list.component';
import { FlightSearchComponent } from './flight-search/flight-search.component';
import { FlightDetailsComponent } from './flight-details/flight-details.component';
import { MaterialImportsModule } from '../shared/material-imports/material-imports.module';
import { PlaceholderComponent } from './placeholder/placeholder.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { PassangerOptionsComponent } from './passanger-options/passanger-options.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PassangerCountComponent } from './passanger-count/passanger-count.component';


@NgModule({
  declarations: [
    FlightListComponent,
    FlightSearchComponent,
    FlightDetailsComponent,
    PlaceholderComponent,
    SearchResultsComponent,
    PassangerOptionsComponent,
    PassangerCountComponent
  ],
  imports: [
    CommonModule,
    FlightRoutingModule,
    MaterialImportsModule,
    ReactiveFormsModule
  ]
})
export class FlightModule { }
