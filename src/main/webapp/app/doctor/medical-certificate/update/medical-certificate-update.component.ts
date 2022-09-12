import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { IMedicalCertificate, MedicalCertificate } from 'app/models/medical-certificate.model';
import { MedicalCertificateService } from 'app/services/medical-certificate.service';
import { IUser } from 'app/models/user-management.model';
import { UserManagementService } from 'app/services/user-management.service';
import { Pagination } from 'app/models/pagination.model';
import { SearchUser } from 'app/models/search-user.model';
import { patientLabel } from 'app/core/util/selectors-util';
import { onlyNumbers } from 'app/shared/validations/input-validation.component';
import _ from 'lodash';
import { ITEMS_SEARCH } from 'app/config/pagination.constants';
@Component({
  selector: 'jhi-medical-certificate-update',
  templateUrl: './medical-certificate-update.component.html',
})
export class MedicalCertificateUpdateComponent implements OnInit {
  isSaving = false;

  patients: IUser[] = [];
  selectedPatient: IUser | null = null;

  editForm = this.fb.group({
    id: [],
    emissionDate: [new Date(), [Validators.required]],
    emissionPlace: [null, [Validators.required, Validators.maxLength(50)]],
    patient: [null, [Validators.required]],
    diagnosis: [null, [Validators.required, Validators.maxLength(255)]],
    cie10Cod: [null, [Validators.required, Validators.maxLength(10)]],
    symptoms: [false, [Validators.required]],
    disease: [false, [Validators.required]],
    diseaseDescription: [null, [Validators.required, Validators.maxLength(255)]],
    insulation: [false, [Validators.required]],
    insulationDescription: [null, [Validators.required, Validators.maxLength(255)]],
    totalDaysOff: ['', [Validators.required, onlyNumbers]],
    fromDate: [new Date(), [Validators.required]],
    untilDate: [new Date(), [Validators.required]],
  });

  constructor(
    protected medicalCertificateService: MedicalCertificateService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected userManagementService: UserManagementService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ medicalCertificate }) => {
      if (!_.isNil(medicalCertificate.id)) {
        this.updateForm(medicalCertificate);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const medicalCertificate = this.createFromForm();
    if (!_.isNil(medicalCertificate.id)) {
      this.subscribeToSaveResponse(this.medicalCertificateService.update(medicalCertificate));
    } else {
      this.subscribeToSaveResponse(this.medicalCertificateService.create(medicalCertificate));
    }
  }

  searchPatient(): void {
    const { patient } = this.editForm.value;
    const pagination = {
      ...new Pagination(),
      size: ITEMS_SEARCH,
    };
    const searchUser = {
      ...new SearchUser(),
      query: patient,
    };

    this.userManagementService.searchPatient(pagination, searchUser).subscribe(resp => {
      this.patients = resp.body!;
    });
  }

  displayPatient(patient: any): string {
    if (patient) {
      return patientLabel(patient);
    }
    return '';
  }

  patientLabelSelector(patient: IUser): string {
    return patientLabel(patient);
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicalCertificate>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(() => this.onSaveSuccess());
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(medicalCertificate: IMedicalCertificate): void {
    this.editForm.patchValue({
      id: medicalCertificate.id,
      patient: medicalCertificate.patient,
      emissionDate: medicalCertificate.emissionDate,
      emissionPlace: medicalCertificate.emissionPlace,
      diagnosis: medicalCertificate.diagnosis,
      cie10Cod: medicalCertificate.cie10Cod,
      symptoms: medicalCertificate.symptoms,
      disease: medicalCertificate.disease,
      diseaseDescription: medicalCertificate.diseaseDescription,
      insulation: medicalCertificate.insulation,
      insulationDescription: medicalCertificate.insulationDescription,
      totalDaysOff: medicalCertificate.totalDaysOff,
      fromDate: medicalCertificate.fromDate,
      untilDate: medicalCertificate.untilDate,
    });
  }

  protected createFromForm(): IMedicalCertificate {
    return {
      ...new MedicalCertificate(),
      id: this.editForm.get(['id'])!.value,
      patient: this.editForm.get(['patient'])!.value,
      emissionDate: this.editForm.get(['emissionDate'])!.value,
      emissionPlace: this.editForm.get(['emissionPlace'])!.value,
      diagnosis: this.editForm.get(['diagnosis'])!.value,
      cie10Cod: this.editForm.get(['cie10Cod'])!.value,
      symptoms: this.editForm.get(['symptoms'])!.value,
      disease: this.editForm.get(['disease'])!.value,
      diseaseDescription: this.editForm.get(['diseaseDescription'])!.value,
      insulation: this.editForm.get(['insulation'])!.value,
      insulationDescription: this.editForm.get(['insulationDescription'])!.value,
      totalDaysOff: this.editForm.get(['totalDaysOff'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      untilDate: this.editForm.get(['untilDate'])!.value,
    };
  }
}
