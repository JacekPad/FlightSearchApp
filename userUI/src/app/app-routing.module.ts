import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FlightSearchComponent } from './flight/flight-search/flight-search.component';
import { NotFoundComponent } from './shared/not-found/not-found.component';
import { BookingRoutingModule } from './booking/booking-routing.module';
import { BookingFlightComponent } from './booking/booking-flight/booking-flight.component';
import { CancelComponent } from './booking/checkout/cancel/cancel.component';
import { SuccessComponent } from './booking/checkout/success/success.component';

const routes: Routes = [
  {path: 'flights', component: FlightSearchComponent},
  {path: '', redirectTo: 'flights', pathMatch: 'full'},
  // {path: 'checkout', loadChildren: () => BookingRoutingModule},
  {path: 'checkout', children: [
    {path: '', component: BookingFlightComponent},
    {path: 'success', component: SuccessComponent},
    {path: 'cancel', component: CancelComponent}
  ]},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
