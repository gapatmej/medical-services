import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

import { AccountService } from 'app/services/account.service';
import { Account } from 'app/models/account.model';
import { LANGUAGES } from 'app/config/language.constants';
import { UserManagementService } from 'app/services/user-management.service';
import { onlyNumbers } from 'app/shared/validations/input-validation.component';
import { Authority } from 'app/config/authority.constants';

@Component({
  selector: 'jhi-settings',
  templateUrl: './settings.component.html',
})
export class SettingsComponent implements OnInit {
  account!: Account;
  success = false;
  languages = LANGUAGES;
  settingsForm = this.fb.group({
    firstName: ['', [Validators.required, Validators.maxLength(50)]],
    lastName: ['', [Validators.required, Validators.maxLength(50)]],
    email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(254), Validators.email]],
    langKey: [undefined],
    address: ['', [Validators.required, Validators.maxLength(100)]],
    contactPhoneNumber: ['', [Validators.required, Validators.maxLength(10), onlyNumbers()]],
    occupation: ['', [Validators.required, Validators.maxLength(100)]],
    certificatePassword: [undefined, [Validators.maxLength(50)]],
  });

  constructor(
    private accountService: AccountService,
    private fb: FormBuilder,
    private translateService: TranslateService,
    private userService: UserManagementService
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.settingsForm.patchValue({
          firstName: account.firstName,
          lastName: account.lastName,
          email: account.email,
          contactPhoneNumber: account.contactPhoneNumber,
          occupation: account.occupation,
          address: account.address,
          langKey: account.langKey,
        });

        this.account = account;
      }
    });
  }

  save(): void {
    this.success = false;

    this.account.firstName = this.settingsForm.get('firstName')!.value;
    this.account.lastName = this.settingsForm.get('lastName')!.value;
    this.account.email = this.settingsForm.get('email')!.value;
    this.account.occupation = this.settingsForm.get('occupation')!.value;
    this.account.address = this.settingsForm.get('address')!.value;
    this.account.contactPhoneNumber = this.settingsForm.get('contactPhoneNumber')!.value;
    this.account.email = this.settingsForm.get('email')!.value;
    this.account.langKey = this.settingsForm.get('langKey')!.value;
    this.account.certificatePassword = this.settingsForm.get('certificatePassword')!.value;

    this.accountService.save(this.account).subscribe(() => {
      this.success = true;

      this.accountService.authenticate(this.account);

      if (this.account.langKey !== this.translateService.currentLang) {
        this.translateService.use(this.account.langKey);
      }
    });
  }

  saveCertificate(event: any): void {
    const fileToUpload = event.target.files.item(0);

    const certificateFormData = new FormData();
    certificateFormData.append('certificate', fileToUpload, fileToUpload.name);
    this.userService.saveCertificate(certificateFormData).subscribe();
  }

  hasAnyAuthority(authorities: string[]): boolean {
    return this.accountService.hasAnyAuthority(authorities);
  }

  get Authority(): typeof Authority {
    return Authority;
  }
}
