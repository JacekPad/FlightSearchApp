import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FlightSearchComponent } from './flight/flight-search/flight-search.component';
import { NotFoundComponent } from './shared/not-found/not-found.component';
import { BookingRoutingModule } from './booking/booking-routing.module';

const routes: Routes = [
  {path: 'flights', component: FlightSearchComponent},
  {path: '', redirectTo: 'flights', pathMatch: 'full'},
  {path: 'booking', loadChildren: () => BookingRoutingModule},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
