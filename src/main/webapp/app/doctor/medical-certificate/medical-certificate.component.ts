import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMedicalCertificate } from 'app/models/medical-certificate.model';

import { ITEMS_PER_PAGE, MAX_SIZE } from 'app/config/pagination.constants';
import { ROUTES } from 'app/config/routes.constants';
import { MedicalCertificateService } from 'app/services/medical-certificate.service';
import { MedicalCertificateDeleteDialogComponent } from 'app/doctor/medical-certificate/delete/medical-certificate-delete-dialog.component';
import { Pagination } from 'app/models/pagination.model';
import { SearchMedicalCertificate } from 'app/models/search-medical-certificate.model';
import { patientLabel } from 'app/core/util/selectors-util';
import { IUser } from 'app/models/user-management.model';
import { MedicalCertificateStatus } from 'app/models/enumeration/medical-certificate-status.model';

@Component({
  selector: 'jhi-medical-certificate',
  templateUrl: './medical-certificate.component.html',
})
export class MedicalCertificateComponent implements OnInit {
  medicalCertificates?: IMedicalCertificate[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  maxSize = MAX_SIZE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected medicalCertificateService: MedicalCertificateService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.isLoading = true;
    this.medicalCertificateService
      .searchDoctorsCertificate(
        {
          ...new Pagination(),
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        },
        {
          ...new SearchMedicalCertificate(),
          query: '',
        }
      )
      .subscribe(
        (res: HttpResponse<IMedicalCertificate[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        () => (this.isLoading = false)
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
      },
    });
  }

  delete(medicalCertificate: IMedicalCertificate): void {
    const modalRef = this.modalService.open(MedicalCertificateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.medicalCertificate = medicalCertificate;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  patientLabel(patient: IUser): string {
    return patientLabel(patient);
  }

  sign(medicalCertificate: IMedicalCertificate): void {
    this.medicalCertificateService.sign(medicalCertificate.id!).subscribe(() => {
      this.loadAll();
    });
  }

  download(medicalCertificate: IMedicalCertificate): void {
    this.medicalCertificateService.download(medicalCertificate.id!).subscribe(({ headers, body }) => {
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

  resend(medicalCertificate: IMedicalCertificate): void {
    this.medicalCertificateService.resend(medicalCertificate.id!).subscribe();
  }

  get MedicalCertificateStatus(): typeof MedicalCertificateStatus {
    return MedicalCertificateStatus;
  }

  get ROUTES(): typeof ROUTES {
    return ROUTES;
  }

  protected sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    return result;
  }

  protected handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.page = page ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === 'asc';
      this.loadAll();
    });
  }

  private onSuccess(medicalCertificates: IMedicalCertificate[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.medicalCertificates = medicalCertificates!;
  }
}
