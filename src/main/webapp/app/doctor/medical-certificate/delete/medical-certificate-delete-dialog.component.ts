import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicalCertificate } from '../../../models/medical-certificate.model';
import { MedicalCertificateService } from '../../../services/medical-certificate.service';

@Component({
  templateUrl: './medical-certificate-delete-dialog.component.html',
})
export class MedicalCertificateDeleteDialogComponent {
  medicalCertificate?: IMedicalCertificate;

  constructor(protected medicalCertificateService: MedicalCertificateService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.medicalCertificateService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
