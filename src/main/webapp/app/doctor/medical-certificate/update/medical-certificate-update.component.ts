import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IMedicalCertificate, MedicalCertificate } from '../medical-certificate.model';
import { MedicalCertificateService } from '../service/medical-certificate.service';

@Component({
  selector: 'jhi-medical-certificate-update',
  templateUrl: './medical-certificate-update.component.html',
})
export class MedicalCertificateUpdateComponent implements OnInit {
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
    protected medicalCertificateService: MedicalCertificateService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalCertificate }) => {
      if (medicalCertificate.id === undefined) {
        const today = dayjs().startOf('day');
        medicalCertificate.emissionDate = today;
        medicalCertificate.attentionDate = today;
        medicalCertificate.fromDate = today;
        medicalCertificate.untilDate = today;
      }

      this.updateForm(medicalCertificate);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medicalCertificate = this.createFromForm();
    if (medicalCertificate.id !== undefined) {
      this.subscribeToSaveResponse(this.medicalCertificateService.update(medicalCertificate));
    } else {
      this.subscribeToSaveResponse(this.medicalCertificateService.create(medicalCertificate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalCertificate>>): void {
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

  protected updateForm(medicalCertificate: IMedicalCertificate): void {
    this.editForm.patchValue({
      id: medicalCertificate.id,
      emissionDate: medicalCertificate.emissionDate ? medicalCertificate.emissionDate.format(DATE_TIME_FORMAT) : null,
      firstName: medicalCertificate.firstName,
      lastName: medicalCertificate.lastName,
      address: medicalCertificate.address,
      clinicHistoryNumber: medicalCertificate.clinicHistoryNumber,
      identificationType: medicalCertificate.identificationType,
      identification: medicalCertificate.identification,
      phone: medicalCertificate.phone,
      mobilePhone: medicalCertificate.mobilePhone,
      attentionDate: medicalCertificate.attentionDate ? medicalCertificate.attentionDate.format(DATE_TIME_FORMAT) : null,
      diagnosis: medicalCertificate.diagnosis,
      restType: medicalCertificate.restType,
      fromDate: medicalCertificate.fromDate ? medicalCertificate.fromDate.format(DATE_TIME_FORMAT) : null,
      untilDate: medicalCertificate.untilDate ? medicalCertificate.untilDate.format(DATE_TIME_FORMAT) : null,
      total: medicalCertificate.total,
      observation: medicalCertificate.observation,
      symptom: medicalCertificate.symptom,
    });
  }

  protected createFromForm(): IMedicalCertificate {
    return {
      ...new MedicalCertificate(),
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
