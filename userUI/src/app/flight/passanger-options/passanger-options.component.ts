import { Component, Inject, OnInit } from '@angular/core';
import { PassangerType, passangerTypeMap } from '../model/enums/passanger-types';
import { flightClassTypesMap } from '../model/enums/flight-class-types';
import { IPassangerCount, PassangerCount } from '../model/passanger-count-model';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-passanger-options',
  templateUrl: './passanger-options.component.html',
  styleUrl: './passanger-options.component.scss'
})
export class PassangerOptionsComponent implements OnInit {
  passangers: IPassangerCount[] = [];
  formGroup!: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) public data: {formGroup: FormGroup}) {}

  ngOnInit(): void {
    this.formGroup = this.data.formGroup;
    Object.keys(PassangerType).forEach(passangerKey => {
      const val = passangerTypeMap.get(passangerKey);
      const passanger: IPassangerCount = new PassangerCount;
      passanger.code = passangerKey;
      passanger.value = val == null ? 'Error' : val;
      if (passangerKey === PassangerType.ADULT) {
        passanger.maxQuantity = [1,2,3,4,5];
      } else{
        passanger.maxQuantity = [0,1,2,3,4];
      }
      this.passangers.push(passanger);
    });
  }

  passangerMap: Map<string, string> = passangerTypeMap;
  flightClassMap: Map<string, string> = flightClassTypesMap;
}
