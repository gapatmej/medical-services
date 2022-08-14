import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { MedicalCertificadeComponent } from '../list/medical-certificade.component';
import { MedicalCertificadeDetailComponent } from '../detail/medical-certificade-detail.component';
import { MedicalCertificadeUpdateComponent } from '../update/medical-certificade-update.component';
import { MedicalCertificadeRoutingResolveService } from './medical-certificade-routing-resolve.service';

const medicalCertificadeRoute: Routes = [
  {
    path: '',
    component: MedicalCertificadeComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MedicalCertificadeDetailComponent,
    resolve: {
      medicalCertificade: MedicalCertificadeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MedicalCertificadeUpdateComponent,
    resolve: {
      medicalCertificade: MedicalCertificadeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MedicalCertificadeUpdateComponent,
    resolve: {
      medicalCertificade: MedicalCertificadeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(medicalCertificadeRoute)],
  exports: [RouterModule],
})
export class MedicalCertificadeRoutingModule {}
