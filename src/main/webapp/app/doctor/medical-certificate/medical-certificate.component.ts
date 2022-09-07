import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicalCertificate } from 'app/models/medical-certificate.model';

import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { ROUTES } from 'app/config/routes.constants';
import { MedicalCertificateService } from 'app/services/medical-certificate.service';
import { MedicalCertificateDeleteDialogComponent } from 'app/doctor/medical-certificate/delete/medical-certificate-delete-dialog.component';
import { Pagination } from 'app/models/pagination.model';
import { SearchMedicalCertificate } from 'app/models/search-medical-certificate.model';
import { patientLabel } from 'app/core/util/selectors-util';
import { IUser } from 'app/admin/user-management/user-management.model';

@Component({
  selector: 'jhi-medical-certificate',
  templateUrl: './medical-certificate.component.html',
})
export class MedicalCertificateComponent implements OnInit {
  medicalCertificates?: IMedicalCertificate[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected medicalCertificateService: MedicalCertificateService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    this.isLoading = true;
    const pageToLoad: number = page ?? this.page ?? 1;

    const pagination = new Pagination();
    const searchMedicalCertificate = {
      ...new SearchMedicalCertificate(),
      query: '',
    };
    this.medicalCertificateService.searchDoctorsCertificate(pagination, searchMedicalCertificate).subscribe(
      (res: HttpResponse<IMedicalCertificate[]>) => {
        this.isLoading = false;
        this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate);
      },
      () => {
        this.isLoading = false;
        this.onError();
      }
    );
  }

  ngOnInit(): void {
    this.handleNavigation();
  }

  trackId(index: number, item: IMedicalCertificate): number {
    return item.id!;
  }

  delete(medicalCertificate: IMedicalCertificate): void {
    const modalRef = this.modalService.open(MedicalCertificateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.medicalCertificate = medicalCertificate;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  patientLabel(patient: IUser): string {
    return patientLabel(patient);
  }

  sign(medicalCertificate: IMedicalCertificate): void {
    this.medicalCertificateService.sign(medicalCertificate.id!).subscribe(() => {
      this.loadPage();
    });
  }

  download(medicalCertificate: IMedicalCertificate): void {
    this.medicalCertificateService.downloadSignedCertificate(medicalCertificate.id!).subscribe(({ headers, body }) => {
      if (body) {
        const blobUrl = URL.createObjectURL(body);
        const aLink = document.createElement('a');
        aLink.href = blobUrl;
        aLink.download = headers.get('filename')!;
        document.body.appendChild(aLink);
        aLink.click();
        document.body.removeChild(aLink);
      }
    });
  }

  get ROUTES(): typeof ROUTES {
    return ROUTES;
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    });
  }

  protected onSuccess(data: IMedicalCertificate[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate([ROUTES.DOCTOR.MEDICAL_CERTIFICATE], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.medicalCertificates = data ?? [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
