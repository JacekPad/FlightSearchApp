import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { FlightRoute } from '../../flight/model/flight-routes';
import { BookingService } from '../booking.service';
import { Observable } from 'rxjs';
import { PassangerType, passangerTypeMap } from '../../flight/model/enums/passanger-types';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';

@Component({
  selector: 'app-booking-flight',
  templateUrl: './booking-flight.component.html',
  styleUrl: './booking-flight.component.scss'
})
export class BookingFlightComponent implements OnInit {

  flightRouteId!: number;
  flightRoute$!: Observable<FlightRoute>;
  flightClass: string = '';
  passangerMap: Map<string, string> = passangerTypeMap;
  bookingForm!: FormGroup;


  constructor(
    private route: ActivatedRoute, private bookingService: BookingService,
    private formBuilder: FormBuilder
  ) { }


  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      // get flightRoute param from ID and start booking process
      this.flightRouteId = params['id'];
      this.flightClass = params['class'];
      this.flightRoute$ = this.bookingService.getBookingById(this.flightRouteId);
      this.initFormGroup();
      this.createForm();
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
    })
  }

  private createForm() {
    this.flightRoute$.subscribe(flightRoute => {
      flightRoute.passangers.forEach(passangerType => {
        for (let n = 0; n < passangerType.number; n++) {
          const formArray = this.bookingForm.get('passangers.'+passangerType.type.toLowerCase()) as FormArray;
          const passanger = this.formBuilder.group({
            name: [n, Validators.required],
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

  calculateCurrency(curr: number): number {
    return curr/100;
  }

  validatePassengerInformation(stepper: MatStepper) {
    this.bookingForm.markAllAsTouched();
    if (this.bookingForm.valid) {
      stepper.next();
    }
  }
}
