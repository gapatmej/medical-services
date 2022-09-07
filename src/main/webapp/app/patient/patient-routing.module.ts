import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'medical-certificate',
        loadChildren: () => import('app/patient/medical-certificate/medical-certificate.module').then(m => m.MedicalCertificateModule),
        data: {
          pageTitle: 'medicalCertificate.home.title',
        },
      },
    ]),
  ],
})
export class PatientRoutingModule {}
