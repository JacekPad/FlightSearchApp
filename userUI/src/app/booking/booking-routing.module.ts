import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookingFlightComponent } from './booking-flight/booking-flight.component';
import { CancelComponent } from './checkout/cancel/cancel.component';
import { SuccessComponent } from './checkout/success/success.component';

const routes: Routes = [
  {path: '', component: BookingFlightComponent},
  {path: 'success', component: SuccessComponent},
  {path: 'cancel', component: CancelComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookingRoutingModule { }
