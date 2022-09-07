import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { MedicalCertificateComponent } from 'app/patient/medical-certificate/medical-certificate.component';
import { MedicalCertificateRoutingModule } from 'app/patient/medical-certificate/route/medical-certificate-routing.module';

@NgModule({
  imports: [SharedModule, MedicalCertificateRoutingModule],
  declarations: [MedicalCertificateComponent, MedicalCertificateComponent],
})
export class MedicalCertificateModule {}
