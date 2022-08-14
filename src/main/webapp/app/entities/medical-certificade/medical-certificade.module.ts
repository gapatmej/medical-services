import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { MedicalCertificadeComponent } from './list/medical-certificade.component';
import { MedicalCertificadeDetailComponent } from './detail/medical-certificade-detail.component';
import { MedicalCertificadeUpdateComponent } from './update/medical-certificade-update.component';
import { MedicalCertificadeDeleteDialogComponent } from './delete/medical-certificade-delete-dialog.component';
import { MedicalCertificadeRoutingModule } from './route/medical-certificade-routing.module';

@NgModule({
  imports: [SharedModule, MedicalCertificadeRoutingModule],
  declarations: [
    MedicalCertificadeComponent,
    MedicalCertificadeDetailComponent,
    MedicalCertificadeUpdateComponent,
    MedicalCertificadeDeleteDialogComponent,
  ],
  entryComponents: [MedicalCertificadeDeleteDialogComponent],
})
export class MedicalCertificadeModule {}
