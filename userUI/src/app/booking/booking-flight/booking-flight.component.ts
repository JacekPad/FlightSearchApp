import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { IFlightRoute } from '../../flight/model/flight-routes';
import { BookingService } from '../booking.service';
import { Observable, take } from 'rxjs';
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

  flightRouteId!: string;
  flightId!: string;
  flightRoute$!: Observable<IFlightRouteBooking>;
  flightClass: string = '';
  passangerMap: Map<string, string> = passangerTypeMap;
  bookingForm!: FormGroup;


  flightRoute!: IFlightRoute;

  constructor(
    private route: ActivatedRoute, private bookingService: BookingService,
    private formBuilder: FormBuilder
  ) { }


  ngOnInit(): void {
    this.route.params.pipe(take(1)).subscribe((params: Params) => {      
      this.route.queryParams.pipe(take(1)).subscribe(param => {
        this.flightClass = param['class'];
      });
      this.flightRouteId = params['routeId'];
      this.flightId = params['id'];
      console.log(this.flightRouteId);
      console.log(this.flightId);
      this.flightRoute$ = this.bookingService.getBookingById(this.flightRouteId, this.flightId, this.flightClass);    
      this.initFormGroup();
      this.createPassengerForm();
    });

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
    this.flightRoute$.subscribe(flightRoute => {
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
