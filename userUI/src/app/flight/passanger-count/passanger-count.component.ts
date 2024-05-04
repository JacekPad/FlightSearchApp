import { Component, Input, OnInit } from '@angular/core';
import { PassangerType, passangerTypeMap } from '../model/enums/passanger-types';
import { IPassangerCount, PassangerCount } from '../model/passanger-count-model';
import { FormGroup } from '@angular/forms';
@Component({
  selector: 'app-passanger-count',
  templateUrl: './passanger-count.component.html',
  styleUrl: './passanger-count.component.scss'
})
export class PassangerCountComponent implements OnInit {
  passangerFormName: string = '';


  ngOnInit(): void {
    this.passangerFormName = this.passanger?.code.toLocaleLowerCase() + 'Number';    
  }

  @Input()
  passanger?: IPassangerCount;

  @Input()
  formGroup!: FormGroup

  increasePassangerQuantity() {
    let val = this.formGroup.get(this.passangerFormName)?.value;
    if (val != null) {
      let newVal = +val + 1;
      if (this.passanger?.code === PassangerType.ADULT) {
        newVal = newVal > 5 ? 5 : newVal;
      } else {
        newVal = newVal > 4 ? 4 : newVal;
      }
      this.formGroup.get(this.passangerFormName)?.setValue(newVal);
    }
  }

  decreasePassangerQuantity() {
    let val = this.formGroup.get(this.passangerFormName)?.value;
    if (val != null) {
      let newVal = +val - 1;
      if (this.passanger?.code === PassangerType.ADULT) {
        newVal = newVal < 1 ? 1 : newVal;
      } else {
        newVal = newVal < 0 ? 0 : newVal;
      }
      this.formGroup.get(this.passangerFormName)?.setValue(newVal);
    }
  }
}
