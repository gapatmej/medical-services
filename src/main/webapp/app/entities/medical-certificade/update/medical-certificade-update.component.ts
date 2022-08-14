import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMedicalCertificade, MedicalCertificade } from '../medical-certificade.model';
import { MedicalCertificadeService } from '../service/medical-certificade.service';

@Component({
  selector: 'jhi-medical-certificade-update',
  templateUrl: './medical-certificade-update.component.html',
})
export class MedicalCertificadeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    emissionDate: [],
    firstName: [],
    lastName: [],
    address: [],
    clinicHistoryNumber: [],
    identificationType: [],
    identification: [],
    phone: [],
    mobilePhone: [],
    attentionDate: [],
    diagnosis: [],
    restType: [],
    fromDate: [],
    untilDate: [],
    total: [],
    observation: [],
    symptom: [],
  });

  constructor(
    protected medicalCertificadeService: MedicalCertificadeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalCertificade }) => {
      if (medicalCertificade.id === undefined) {
        const today = dayjs().startOf('day');
        medicalCertificade.emissionDate = today;
        medicalCertificade.attentionDate = today;
        medicalCertificade.fromDate = today;
        medicalCertificade.untilDate = today;
      }

      this.updateForm(medicalCertificade);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medicalCertificade = this.createFromForm();
    if (medicalCertificade.id !== undefined) {
      this.subscribeToSaveResponse(this.medicalCertificadeService.update(medicalCertificade));
    } else {
      this.subscribeToSaveResponse(this.medicalCertificadeService.create(medicalCertificade));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalCertificade>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(medicalCertificade: IMedicalCertificade): void {
    this.editForm.patchValue({
      id: medicalCertificade.id,
      emissionDate: medicalCertificade.emissionDate ? medicalCertificade.emissionDate.format(DATE_TIME_FORMAT) : null,
      firstName: medicalCertificade.firstName,
      lastName: medicalCertificade.lastName,
      address: medicalCertificade.address,
      clinicHistoryNumber: medicalCertificade.clinicHistoryNumber,
      identificationType: medicalCertificade.identificationType,
      identification: medicalCertificade.identification,
      phone: medicalCertificade.phone,
      mobilePhone: medicalCertificade.mobilePhone,
      attentionDate: medicalCertificade.attentionDate ? medicalCertificade.attentionDate.format(DATE_TIME_FORMAT) : null,
      diagnosis: medicalCertificade.diagnosis,
      restType: medicalCertificade.restType,
      fromDate: medicalCertificade.fromDate ? medicalCertificade.fromDate.format(DATE_TIME_FORMAT) : null,
      untilDate: medicalCertificade.untilDate ? medicalCertificade.untilDate.format(DATE_TIME_FORMAT) : null,
      total: medicalCertificade.total,
      observation: medicalCertificade.observation,
      symptom: medicalCertificade.symptom,
    });
  }

  protected createFromForm(): IMedicalCertificade {
    return {
      ...new MedicalCertificade(),
      id: this.editForm.get(['id'])!.value,
      emissionDate: this.editForm.get(['emissionDate'])!.value
        ? dayjs(this.editForm.get(['emissionDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      address: this.editForm.get(['address'])!.value,
      clinicHistoryNumber: this.editForm.get(['clinicHistoryNumber'])!.value,
      identificationType: this.editForm.get(['identificationType'])!.value,
      identification: this.editForm.get(['identification'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      mobilePhone: this.editForm.get(['mobilePhone'])!.value,
      attentionDate: this.editForm.get(['attentionDate'])!.value
        ? dayjs(this.editForm.get(['attentionDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      diagnosis: this.editForm.get(['diagnosis'])!.value,
      restType: this.editForm.get(['restType'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? dayjs(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      untilDate: this.editForm.get(['untilDate'])!.value ? dayjs(this.editForm.get(['untilDate'])!.value, DATE_TIME_FORMAT) : undefined,
      total: this.editForm.get(['total'])!.value,
      observation: this.editForm.get(['observation'])!.value,
      symptom: this.editForm.get(['symptom'])!.value,
    };
  }
}
