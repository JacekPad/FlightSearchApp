import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookingFlightComponent } from './booking-flight/booking-flight.component';

const routes: Routes = [
  {path: ':id', component: BookingFlightComponent},
  {path: ':routeId/:id', component: BookingFlightComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookingRoutingModule { }
