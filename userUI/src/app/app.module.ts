import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { NotFoundComponent } from './shared/not-found/not-found.component';
import { NavBarComponent } from './shared/nav-bar/nav-bar.component';
import { FooterComponent } from './shared/footer/footer.component';
import { FlightModule } from './flight/flight.module';
import { MaterialImportsModule } from './shared/material-imports/material-imports.module';
import { BookingModule } from './booking/booking.module';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    NavBarComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FlightModule,
    BookingModule,
    MaterialImportsModule
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
