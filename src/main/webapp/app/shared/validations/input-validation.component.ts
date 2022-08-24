import { Component, Input } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';
import { REGEX } from 'app/config/regex.constants';
import _ from 'lodash';

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
