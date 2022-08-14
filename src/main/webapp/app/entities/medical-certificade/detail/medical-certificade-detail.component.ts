import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicalCertificade } from '../medical-certificade.model';

@Component({
  selector: 'jhi-medical-certificade-detail',
  templateUrl: './medical-certificade-detail.component.html',
})
export class MedicalCertificadeDetailComponent implements OnInit {
  medicalCertificade: IMedicalCertificade | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalCertificade }) => {
      this.medicalCertificade = medicalCertificade;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
