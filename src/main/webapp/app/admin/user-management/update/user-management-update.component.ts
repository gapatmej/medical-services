import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { LANGUAGES } from 'app/config/language.constants';
import { User } from '../../../models/user-management.model';
import { UserManagementService } from '../../../services/user-management.service';
import { IdentificationType } from 'app/models/enumeration/identification-type.model';
import { onlyNumbers, validateDni } from 'app/shared/validations/input-validation.component';
import { getAutorityName } from 'app/core/util/enumeration-util';
import { Authority } from 'app/config/authority.constants';
import _ from 'lodash-es';

@Component({
  selector: 'jhi-user-mgmt-update',
  templateUrl: './user-management-update.component.html',
})
export class UserManagementUpdateComponent implements OnInit {
  user!: User;
  languages = LANGUAGES;
  authorities: string[] = [];
  isSaving = false;
  identificationTypes = Object.values(IdentificationType);

  editForm = this.fb.group({
    id: [],
    identificationType: ['', [Validators.required]],
    dni: ['', [Validators.required, Validators.maxLength(10), validateDni()]],
    firstName: ['', [Validators.required, Validators.maxLength(50)]],
    lastName: ['', [Validators.required, Validators.maxLength(50)]],
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    address: ['', [Validators.required, Validators.maxLength(100)]],
    contactPhoneNumber: ['', [Validators.required, Validators.maxLength(10), onlyNumbers()]],
    occupation: ['', [Validators.required, Validators.maxLength(100)]],
    activated: [],
    langKey: [],
    authorities: [[], [Validators.required]],
  });

  constructor(private userService: UserManagementService, private route: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => {
      if (user) {
        this.user = user;
        if (this.user.id === undefined) {
          this.user.activated = true;
        }
        this.updateForm();
        this.verifyAuthorities();
      }
    });
    this.userService.authorities().subscribe(authorities => (this.authorities = authorities));
    this.verifyDniValidators();
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    this.updateUser(this.user);
    if (this.user.id !== undefined) {
      this.userService.update(this.user).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    } else {
      this.userService.create(this.user).subscribe(
        () => this.onSaveSuccess(),
        () => this.onSaveError()
      );
    }
  }

  onIdentificationTypeChange(): void {
    this.verifyDniValidators();
  }

  onSelectAuthority(): void {
    this.verifyAuthorities();
  }

  verifyAuthorities(): void {
    const { authorities } = this.editForm.value;

    if (_.includes(authorities, Authority.PATIENT)) {
      this.editForm.addControl(
        'medicalHistoryNumber',
        new FormControl(this.user.medicalHistoryNumber, [Validators.required, Validators.maxLength(10)])
      );
    } else {
      this.editForm.removeControl('medicalHistoryNumber');
    }
  }

  verifyDniValidators = (): void => {
    const { identificationType } = this.editForm.value;
    const dniControl = this.editForm.controls['dni']!;
    dniControl.clearValidators();

    if (IdentificationType.DNI === identificationType) {
      dniControl.setValidators([Validators.required, Validators.maxLength(10), validateDni()]);
    } else {
      dniControl.setValidators([Validators.required, Validators.maxLength(10)]);
    }

    dniControl.updateValueAndValidity();
  };

  autorityName = (i: string): string => getAutorityName(i);

  private updateForm(): void {
    this.editForm.patchValue({
      identificationType: this.user.identificationType,
      dni: this.user.dni,
      id: this.user.id,
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email,
      address: this.user.address,
      contactPhoneNumber: this.user.contactPhoneNumber,
      occupation: this.user.occupation,
      activated: this.user.activated,
      langKey: this.user.langKey,
      authorities: this.user.authorities,
    });
  }

  private updateUser(user: User): void {
    const { medicalHistoryNumber } = this.editForm.value;
    user.identificationType = this.editForm.get(['identificationType'])!.value;
    user.dni = this.editForm.get(['dni'])!.value;
    user.firstName = this.editForm.get(['firstName'])!.value;
    user.lastName = this.editForm.get(['lastName'])!.value;
    user.email = this.editForm.get(['email'])!.value;
    user.address = this.editForm.get(['address'])!.value;
    user.contactPhoneNumber = this.editForm.get(['contactPhoneNumber'])!.value;
    user.occupation = this.editForm.get(['occupation'])!.value;
    user.medicalHistoryNumber = medicalHistoryNumber;
    user.activated = this.editForm.get(['activated'])!.value;
    user.langKey = this.editForm.get(['langKey'])!.value;
    user.authorities = this.editForm.get(['authorities'])!.value;
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }
}
