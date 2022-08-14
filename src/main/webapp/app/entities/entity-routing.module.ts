import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'medical-certificade',
        data: { pageTitle: 'medicalServicesApp.medicalCertificade.home.title' },
        loadChildren: () => import('./medical-certificade/medical-certificade.module').then(m => m.MedicalCertificadeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
