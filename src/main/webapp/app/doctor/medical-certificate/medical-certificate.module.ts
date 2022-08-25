import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { MedicalCertificateComponent } from './list/medical-certificate.component';
import { MedicalCertificateUpdateComponent } from './update/medical-certificate-update.component';
import { MedicalCertificateDeleteDialogComponent } from './delete/medical-certificate-delete-dialog.component';
import { MedicalCertificateRoutingModule } from './route/medical-certificate-routing.module';

@NgModule({
  imports: [SharedModule, MedicalCertificateRoutingModule],
  declarations: [MedicalCertificateComponent, MedicalCertificateUpdateComponent, MedicalCertificateDeleteDialogComponent],
  entryComponents: [MedicalCertificateDeleteDialogComponent],
})
export class MedicalCertificateModule {}
