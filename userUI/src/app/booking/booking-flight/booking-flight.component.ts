import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IFlightRoute } from '../../flight/model/flight-routes';
import { BookingService } from '../booking.service';
import { Observable } from 'rxjs';
import { passangerTypeMap } from '../../flight/model/enums/passanger-types';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { IFlightRouteBooking } from '../../flight/model/flight-route-booking-model';

@Component({
  selector: 'app-booking-flight',
  templateUrl: './booking-flight.component.html',
  styleUrl: './booking-flight.component.scss'
})
export class BookingFlightComponent implements OnInit {

  flightRouteDeparture$!: Observable<IFlightRouteBooking>;
  flightRouteReturn$!: Observable<IFlightRouteBooking>;
  passangerMap: Map<string, string> = passangerTypeMap;
  bookingForm!: FormGroup;
  flightRoute!: IFlightRoute;

  constructor(
    private route: ActivatedRoute,
     private bookingService: BookingService,
    private formBuilder: FormBuilder
  ) { }


  ngOnInit(): void {
    this.getDepartureRoute();
    if (this.bookingService.bookingChoice.returnFlight != null) {
        this.getReturnRoute();
    }
    this.initFormGroup();
    this.createPassengerForm();
  }

  private getDepartureRoute() {
    const flightRouteId = this.bookingService.bookingChoice.departureFlight.routeId
    const flightId = this.bookingService.bookingChoice.departureFlight.flightId
    const flightClass = this.bookingService.bookingChoice.departureFlight.flightClass
    this.flightRouteDeparture$ = this.bookingService.getBookingById(flightRouteId, flightId, flightClass);   
  }

  private getReturnRoute() {
    const flightRouteIdReturn = this.bookingService.bookingChoice.returnFlight.routeId
    const flightIdReturn = this.bookingService.bookingChoice.returnFlight.flightId
    const flightClassReturn = this.bookingService.bookingChoice.returnFlight.flightClass
    this.flightRouteReturn$ = this.bookingService.getBookingById(flightRouteIdReturn, flightIdReturn, flightClassReturn);
  }

  private initFormGroup(): void {
    this.bookingForm = this.formBuilder.group({
      email: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      passangers: this.formBuilder.group({
        adult: this.formBuilder.array([]),
        child: this.formBuilder.array([]),
        infant: this.formBuilder.array([]),
      })
    });
  }

  private createPassengerForm() {
    this.flightRouteDeparture$.subscribe(flightRoute => {
      flightRoute.passengers.forEach(passangerType => {
        for (let n = 0; n < passangerType.quantity; n++) {
          const formArray = this.bookingForm.get('passangers.'+passangerType.type.toLowerCase()) as FormArray;
          const passanger = this.formBuilder.group({
            name: ['', Validators.required],
            surname: ['', Validators.required]
          });
          formArray.push(passanger);
        };
      });
    });
  }

  createPassangersArray(n: number): number[] {
    let arr = [];
    for (let i = 1; i <= n; i++) arr.push(i);
    return arr;
  }

  passangerName(passanger: string) {
    return passangerTypeMap.get(passanger);
  }

  calculateCurrency(price: number): number {
    return price ? price/100 : 0;
  }

  validatePassengerInformation(stepper: MatStepper) {
    this.bookingForm.markAllAsTouched();
    if (this.bookingForm.valid) {
      stepper.next();
    }
  }
}
