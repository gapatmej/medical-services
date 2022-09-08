import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { onlyNumbers } from 'app/shared/validations/input-validation.component';
import { Organization } from 'app/models/organization.model';
import { OrganizationService } from 'app/services/organization.service';

@Component({
  templateUrl: './organization-update.component.html',
})
export class OrganizationUpdateComponent implements OnInit {
  organization!: Organization;

  editForm = this.fb.group({
    id: [],
    name: ['', [Validators.required, Validators.maxLength(150)]],
    email: ['', [Validators.required, Validators.maxLength(100), Validators.email]],
    phoneNumber: ['', [Validators.required, Validators.maxLength(20), onlyNumbers()]],
    address: ['', [Validators.required, Validators.maxLength(255)]],
  });

  constructor(private organizationService: OrganizationService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.organizationService.getOrganization().subscribe(({ body }) => this.updateForm(body));
  }

  save(): void {
    this.updateEntity();
    this.organizationService.save(this.organization).subscribe();
  }

  private updateForm(organization: Organization | null): void {
    if (organization) {
      this.organization = organization;
      this.editForm.patchValue({
        name: organization.name,
        email: organization.email,
        phoneNumber: organization.phoneNumber,
        address: organization.address,
      });
    }
  }

  private updateEntity(): void {
    this.organization.name = this.editForm.get(['name'])!.value;
    this.organization.email = this.editForm.get(['email'])!.value;
    this.organization.phoneNumber = this.editForm.get(['phoneNumber'])!.value;
    this.organization.address = this.editForm.get(['address'])!.value;
  }
}
