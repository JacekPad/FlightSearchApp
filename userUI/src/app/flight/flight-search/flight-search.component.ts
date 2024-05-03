import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PassangerOptionsComponent } from '../passanger-options/passanger-options.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { flightTypesMap, FlightTypes } from './flight-types';

@Component({
  selector: 'app-flight-search',
  templateUrl: './flight-search.component.html',
  styleUrl: './flight-search.component.scss'
})
export class FlightSearchComponent implements OnInit {

  searchForm!: FormGroup;
  flightTypes: Map<string,string> = flightTypesMap;
  isNonStopFlight: boolean = true;

  constructor(
    private dialog: MatDialog,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      flightType: [FlightTypes.ONEWAY],
      maxStops: [1],
      departureAirport: [''],
      arrivalAirport: [''],
      departureDate: [null],
      returnDate: [null]
    });
  }

  openPassangerDialog() {

    const dialog = this.dialog.open(PassangerOptionsComponent)

    dialog.afterClosed().subscribe(result => {
      console.log(result);

    })
  }

  onNonstopChange() {
    this.isNonStopFlight = !this.isNonStopFlight;
    if (this.isNonStopFlight) {
      this.searchForm.get('maxStops')?.setValue(1);
    }

  }

  increaseMaxStops() {
    let val = this.searchForm.get('maxStops')?.value;
    if (val != null) {
      let newVal = +val + 1;
      newVal = newVal > 5 ? 5 : newVal;
      this.searchForm.get('maxStops')?.setValue(newVal);
    }
  }

  decreaseMaxStops() {
    let val = this.searchForm.get('maxStops')?.value;
    if (val != null) {
      let newVal = +val - 1;
      newVal = newVal < 1 ? 1 : newVal;
      this.searchForm.get('maxStops')?.setValue(newVal);
    }
  }

  isOnewayFlight():boolean {
    return this.searchForm.get('flightType')?.value === FlightTypes.ONEWAY;
  }

  onFlightTypeChange() {
    this.searchForm.get('departureDate')?.reset();
    this.searchForm.get('returnDate')?.reset();
  }

}
