import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MedicalCertificateComponent } from 'app/patient/medical-certificate/medical-certificate.component';

const medicalCertificateRoute: Routes = [
  {
    path: '',
    component: MedicalCertificateComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(medicalCertificateRoute)],
  exports: [RouterModule],
})
export class MedicalCertificateRoutingModule {}
