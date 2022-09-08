import { Component, AfterViewInit, ElementRef, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { PasswordResetInitService } from './password-reset-init.service';

@Component({
  selector: 'jhi-password-reset-init',
  templateUrl: './password-reset-init.component.html',
})
export class PasswordResetInitComponent implements AfterViewInit {
  @ViewChild('login', { static: false })
  login?: ElementRef;

  success = false;
  resetRequestForm = this.fb.group({
    login: ['', [Validators.required, Validators.maxLength(50)]],
  });

  constructor(private passwordResetInitService: PasswordResetInitService, private fb: FormBuilder) {}

  ngAfterViewInit(): void {
    if (this.login) {
      this.login.nativeElement.focus();
    }
  }

  requestReset(): void {
    this.passwordResetInitService.save(this.resetRequestForm.get(['login'])!.value).subscribe(() => (this.success = true));
  }
}
