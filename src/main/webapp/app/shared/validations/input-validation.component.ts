import { Component, Input } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';
import { REGEX } from 'app/config/regex.constants';
import _ from 'lodash-es';

@Component({
  selector: 'jhi-input-validation',
  templateUrl: './input-validation.component.html',
})
export class InputValidationComponent {
  @Input() control: AbstractControl | null = null;
  @Input() enableTouched = true;
  @Input() showRequired = true;
}

export function onlyNumbers(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    let forbidden = false;
    if (!_.isEmpty(control.value) && !_.isNil(control.value)) {
      const regext = _.cloneDeep(REGEX.NUMBER_CHARACTER);
      forbidden = !regext.test(control.value);
    }
    return forbidden ? { forbiddenOnlyNumbers: { value: control.value } } : null;
  };
}

export function validateDni(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const cedula = control.value;
    const array = cedula.split('');
    const num = array.length;
    let forbidden = true;
    if (num === 10) {
      let total = 0;
      const digito = array[9] * 1;
      for (let i = 0; i < num - 1; i++) {
        let mult = 0;
        if (i % 2 !== 0) {
          total = total + array[i] * 1;
        } else {
          mult = array[i] * 2;
          if (mult > 9) {
            total = total + (mult - 9);
          } else {
            total = total + mult;
          }
        }
      }
      let decena = total / 10;
      decena = Math.floor(decena);
      decena = (decena + 1) * 10;
      const final = decena - total;
      if ((final === 10 && digito === 0) || final === digito) {
        forbidden = false;
      }
    }

    return forbidden ? { forbiddenDni: { value: control.value } } : null;
  };
}
