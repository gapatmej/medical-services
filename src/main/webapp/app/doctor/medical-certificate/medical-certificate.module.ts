import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { MedicalCertificateComponent } from './medical-certificate.component';
import { MedicalCertificateUpdateComponent } from './update/medical-certificate-update.component';
import { MedicalCertificateDeleteDialogComponent } from './delete/medical-certificate-delete-dialog.component';
import { MedicalCertificateRoutingModule } from './route/medical-certificate-routing.module';

import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

@NgModule({
  imports: [
    SharedModule,
    MedicalCertificateRoutingModule,
    MatAutocompleteModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSlideToggleModule,
  ],
  declarations: [MedicalCertificateComponent, MedicalCertificateUpdateComponent, MedicalCertificateDeleteDialogComponent],
  entryComponents: [MedicalCertificateDeleteDialogComponent],
})
export class MedicalCertificateModule {}
