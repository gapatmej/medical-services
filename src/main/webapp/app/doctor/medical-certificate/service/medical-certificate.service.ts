import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMedicalCertificate } from '../medical-certificate.model';
import { ISearchMedicalCertificate } from '../search-medical-certificate.model';
import { Pagination } from 'app/core/request/pagination.model';

export type EntityResponseType = HttpResponse<IMedicalCertificate>;
export type EntityArrayResponseType = HttpResponse<IMedicalCertificate[]>;

@Injectable({ providedIn: 'root' })
export class MedicalCertificateService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/medical-certificates');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(medicalCertificate: IMedicalCertificate): Observable<EntityResponseType> {
    return this.http.post<IMedicalCertificate>(this.resourceUrl, medicalCertificate, { observe: 'response' });
  }

  update(medicalCertificate: IMedicalCertificate): Observable<EntityResponseType> {
    return this.http.put<IMedicalCertificate>(`${this.resourceUrl}`, medicalCertificate, {
      observe: 'response',
    });
  }

  searchById(id: number): Observable<EntityResponseType> {
    return this.http.get<IMedicalCertificate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  searchDoctor(pagination: Pagination, searchMedicalCertificate: ISearchMedicalCertificate): Observable<EntityArrayResponseType> {
    const options = createRequestOption(pagination);
    return this.http
      .post<IMedicalCertificate[]>(`${this.resourceUrl}/doctor/search`, searchMedicalCertificate, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((medicalCertificate: IMedicalCertificate) => {
        medicalCertificate.emissionDate = medicalCertificate.emissionDate ? dayjs(medicalCertificate.emissionDate) : undefined;
        medicalCertificate.fromDate = medicalCertificate.fromDate ? dayjs(medicalCertificate.fromDate) : undefined;
        medicalCertificate.untilDate = medicalCertificate.untilDate ? dayjs(medicalCertificate.untilDate) : undefined;
      });
    }
    return res;
  }
}
