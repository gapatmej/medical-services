import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MedicalCertificateComponent } from 'app/doctor/medical-certificate/medical-certificate.component';
import { MedicalCertificateRoutingResolveService } from 'app/doctor/medical-certificate/route/medical-certificate-routing-resolve.service';
import { MedicalCertificateUpdateComponent } from '../update/medical-certificate-update.component';

const medicalCertificateRoute: Routes = [
  {
    path: '',
    component: MedicalCertificateComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MedicalCertificateUpdateComponent,
    resolve: {
      medicalCertificate: MedicalCertificateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MedicalCertificateUpdateComponent,
    resolve: {
      medicalCertificate: MedicalCertificateRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(medicalCertificateRoute)],
  exports: [RouterModule],
})
export class MedicalCertificateRoutingModule {}
