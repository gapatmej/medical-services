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
import { internationalDiseaseLabel, userLabel } from 'app/core/util/selectors-util';
import { onlyNumbers } from 'app/shared/validations/input-validation.component';
import _ from 'lodash-es';
import { ITEMS_SEARCH } from 'app/config/pagination.constants';
import { SearchInternationalDisease } from 'app/models/search-international-disease.model';
import { InternationalDiseaseService } from 'app/services/international-disease.service';
import { IInternationalDisease } from 'app/models/international-disease.model';
import { ContingencyType } from 'app/models/enumeration/contingency-type.model';
@Component({
  selector: 'jhi-medical-certificate-update',
  templateUrl: './medical-certificate-update.component.html',
})
export class MedicalCertificateUpdateComponent implements OnInit {
  isSaving = false;

  patients: IUser[] = [];
  internationalDiseases: IInternationalDisease[] = [];

  editForm = this.fb.group({
    id: [],
    emissionDate: [new Date(), [Validators.required]],
    patient: [null, [Validators.required]],
    diagnosis: [null, [Validators.required, Validators.maxLength(255)]],
    internationalDisease: [null, [Validators.required]],
    symptoms: [false, [Validators.required]],
    disease: [false, [Validators.required]],
    diseaseDescription: [null, [Validators.required, Validators.maxLength(255)]],
    insulation: [false, [Validators.required]],
    insulationDescription: [null, [Validators.required, Validators.maxLength(255)]],
    totalDaysOff: ['', [Validators.required, onlyNumbers]],
    fromDate: [new Date(), [Validators.required]],
    untilDate: [new Date(), [Validators.required]],
    contingencyType: [null, [Validators.required]],
  });

  constructor(
    protected medicalCertificateService: MedicalCertificateService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected userManagementService: UserManagementService,
    protected internationalDiseaseService: InternationalDiseaseService
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

  searchInternationalDisease(): void {
    const { internationalDisease } = this.editForm.value;
    const pagination = {
      ...new Pagination(),
      size: ITEMS_SEARCH,
    };
    const searchInternationalDisease = {
      ...new SearchInternationalDisease(),
      query: internationalDisease,
    };

    this.internationalDiseaseService.search(pagination, searchInternationalDisease).subscribe(resp => {
      this.internationalDiseases = resp.body!;
    });
  }

  displayPatient(patient: any): string {
    if (patient) {
      return userLabel(patient);
    }
    return '';
  }

  patientLabelSelector(patient: IUser): string {
    return userLabel(patient);
  }

  displayInternationalDisease(internationalDisease: any): string {
    if (internationalDisease) {
      return internationalDiseaseLabel(internationalDisease);
    }
    return '';
  }

  internationalDiseaseLabelSelector(internationalDisease: IInternationalDisease): string {
    return internationalDiseaseLabel(internationalDisease);
  }

  get ContingencyType(): typeof ContingencyType {
    return ContingencyType;
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
      diagnosis: medicalCertificate.diagnosis,
      internationalDisease: medicalCertificate.internationalDisease,
      symptoms: medicalCertificate.symptoms,
      disease: medicalCertificate.disease,
      diseaseDescription: medicalCertificate.diseaseDescription,
      insulation: medicalCertificate.insulation,
      insulationDescription: medicalCertificate.insulationDescription,
      totalDaysOff: medicalCertificate.totalDaysOff,
      fromDate: medicalCertificate.fromDate,
      untilDate: medicalCertificate.untilDate,
      contingencyType: medicalCertificate.contingencyType,
    });
  }

  protected createFromForm(): IMedicalCertificate {
    return {
      ...new MedicalCertificate(),
      id: this.editForm.get(['id'])!.value,
      patient: this.editForm.get(['patient'])!.value,
      emissionDate: this.editForm.get(['emissionDate'])!.value,
      diagnosis: this.editForm.get(['diagnosis'])!.value,
      internationalDisease: this.editForm.get(['internationalDisease'])!.value,
      symptoms: this.editForm.get(['symptoms'])!.value,
      disease: this.editForm.get(['disease'])!.value,
      diseaseDescription: this.editForm.get(['diseaseDescription'])!.value,
      insulation: this.editForm.get(['insulation'])!.value,
      insulationDescription: this.editForm.get(['insulationDescription'])!.value,
      totalDaysOff: this.editForm.get(['totalDaysOff'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      untilDate: this.editForm.get(['untilDate'])!.value,
      contingencyType: this.editForm.get(['contingencyType'])!.value,
    };
  }
}
