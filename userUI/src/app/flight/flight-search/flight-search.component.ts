import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PassangerOptionsComponent } from '../passanger-options/passanger-options.component';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { flightTypesMap, FlightTypes } from '../model/enums/flight-types';
import { FlightClassTypes, flightClassTypesMap } from '../model/enums/flight-class-types';
import { Observable, combineLatest, map, startWith } from 'rxjs';
import { ISearchParams, SearchParams } from '../model/search-params';
import { FlightService } from '../flight.service';
import { FlightRoute } from '../model/flight-routes';
import { IFlightRouteResponse } from '../model/flight-route-response';

@Component({
  selector: 'app-flight-search',
  templateUrl: './flight-search.component.html',
  styleUrl: './flight-search.component.scss'
})
export class FlightSearchComponent implements OnInit {

  searchForm!: FormGroup;
  flightTypes: Map<FlightTypes, string> = flightTypesMap;
  isNonStopFlight: boolean = true;

  passangerNumber: string = '1 traveller';
  flightClass?: string;

  flights$?: Observable<IFlightRouteResponse>

  constructor(
    private dialog: MatDialog,
    private formBuilder: FormBuilder,
    private flightService: FlightService
  ) { }

  ngOnInit(): void {
    this.initFormGroup();
    this.calculateNumOfPassangers();
    this.getFlightClass();
  }

  private initFormGroup(): void {
    this.searchForm = this.formBuilder.group({
      flightType: [FlightTypes.ONEWAY, Validators.required],
      flightClass: [FlightClassTypes.ECONOMY, Validators.required],
      maxStops: [1, Validators.required],
      departureAirport: ['CCF', Validators.required],
      arrivalAirport: ['BMK', Validators.required],
      // departureDate: [null, [Validators.required, this.departureDateValidator()]],
      departureDate: [null, [Validators.required]],
      returnDate: [null],
      adultNumber: [1, Validators.required],
      childNumber: [0, Validators.required],
      infantNumber: [0, Validators.required]
    });
  }

  openPassangerDialog() {
    const dialog = this.dialog.open(PassangerOptionsComponent, {
      data: { formGroup: this.searchForm },
      width: '30%'
    });
    dialog.afterClosed().subscribe(result => {

    });
  }

  onNonstopChange() {
    this.isNonStopFlight = !this.isNonStopFlight;
    if (this.isNonStopFlight) {
      this.searchForm.controls['maxStops'].setValue(1);
    }

  }

  increaseMaxStops() {
    let val = this.searchForm.controls['maxStops'].value;
    if (val != null) {
      let newVal = +val + 1;
      newVal = newVal > 5 ? 5 : newVal;
      this.searchForm.controls['maxStops'].setValue(newVal);
    }
  }

  decreaseMaxStops() {
    let val = this.searchForm.controls['maxStops'].value;
    if (val != null) {
      let newVal = +val - 1;
      newVal = newVal < 1 ? 1 : newVal;
      this.searchForm.controls['maxStops'].setValue(newVal);
    }
  }

  isOnewayFlight(): boolean {
    return this.searchForm.controls['flightType'].value === FlightTypes.ONEWAY;
  }

  onFlightTypeChange(event: string) {
    if (event === FlightTypes.ROUND) {
      this.searchForm.controls['returnDate'].setValidators([Validators.required]);
    } else {
      this.searchForm.controls['returnDate'].setValidators(null);
    }
    this.searchForm.controls['departureDate'].reset();
    this.searchForm.controls['returnDate'].reset();
  }

  searchForFlights() {
    this.searchForm.markAllAsTouched();
    if (this.searchForm.valid) {
      let params: ISearchParams = new SearchParams;
      params = <SearchParams> this.searchForm.value;
      this.flights$ = this.flightService.getFlights(params);
    }
  }

  private calculateNumOfPassangers(): void {
    let numOfTravellers = 1;
    const adultObs = this.searchForm.controls['adultNumber'].valueChanges.pipe(startWith(this.searchForm.controls['adultNumber'].value));
    const childObs = this.searchForm.controls['childNumber'].valueChanges.pipe(startWith(this.searchForm.controls['childNumber'].value));
    const infantObs = this.searchForm.controls['infantNumber'].valueChanges.pipe(startWith(this.searchForm.controls['infantNumber'].value));
    combineLatest([adultObs, childObs, infantObs]).subscribe(([adults, children, infants]) => {
      numOfTravellers = adults + children + infants;
      if (numOfTravellers === 1) {
        this.passangerNumber = numOfTravellers + ' traveller';
      } else {
        this.passangerNumber = numOfTravellers + ' travellers'
      }
    });
  }

  private getFlightClass() {
    this.searchForm.controls['flightClass'].valueChanges.pipe(startWith(this.searchForm.controls['flightClass'].value)).subscribe(flightClass => {
      this.flightClass = flightClassTypesMap.get(flightClass);
    });
  }

  private departureDateValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const date = control.value == null ? null : new Date(control.value);
      const today = new Date();
      if (date != null && date < today) {
        return { dateBeforeToday: true };
      } else {
        return null;
      }
    };
  }
}
