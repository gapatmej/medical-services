import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'medical-certificate',
        loadChildren: () => import('app/doctor/medical-certificate/medical-certificate.module').then(m => m.MedicalCertificateModule),
        data: {
          pageTitle: 'medicalServicesApp.medicalCertificate.home.title',
        },
      },
    ]),
  ],
})
export class DoctorRoutingModule {}
