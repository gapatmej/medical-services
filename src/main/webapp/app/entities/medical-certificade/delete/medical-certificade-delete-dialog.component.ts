import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicalCertificade } from '../medical-certificade.model';
import { MedicalCertificadeService } from '../service/medical-certificade.service';

@Component({
  templateUrl: './medical-certificade-delete-dialog.component.html',
})
export class MedicalCertificadeDeleteDialogComponent {
  medicalCertificade?: IMedicalCertificade;

  constructor(protected medicalCertificadeService: MedicalCertificadeService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.medicalCertificadeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
