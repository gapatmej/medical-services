import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMedicalCertificate, getMedicalCertificateIdentifier } from '../medical-certificate.model';

export type EntityResponseType = HttpResponse<IMedicalCertificate>;
export type EntityArrayResponseType = HttpResponse<IMedicalCertificate[]>;

@Injectable({ providedIn: 'root' })
export class MedicalCertificateService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/medical-certificates');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(medicalCertificate: IMedicalCertificate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalCertificate);
    return this.http
      .post<IMedicalCertificate>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicalCertificate: IMedicalCertificate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalCertificate);
    return this.http
      .put<IMedicalCertificate>(`${this.resourceUrl}/${getMedicalCertificateIdentifier(medicalCertificate) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(medicalCertificate: IMedicalCertificate): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalCertificate);
    return this.http
      .patch<IMedicalCertificate>(`${this.resourceUrl}/${getMedicalCertificateIdentifier(medicalCertificate) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicalCertificate>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicalCertificate[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMedicalCertificateToCollectionIfMissing(
    medicalCertificateCollection: IMedicalCertificate[],
    ...medicalCertificatesToCheck: (IMedicalCertificate | null | undefined)[]
  ): IMedicalCertificate[] {
    const medicalCertificates: IMedicalCertificate[] = medicalCertificatesToCheck.filter(isPresent);
    if (medicalCertificates.length > 0) {
      const medicalCertificateCollectionIdentifiers = medicalCertificateCollection.map(
        medicalCertificateItem => getMedicalCertificateIdentifier(medicalCertificateItem)!
      );
      const medicalCertificatesToAdd = medicalCertificates.filter(medicalCertificateItem => {
        const medicalCertificateIdentifier = getMedicalCertificateIdentifier(medicalCertificateItem);
        if (medicalCertificateIdentifier == null || medicalCertificateCollectionIdentifiers.includes(medicalCertificateIdentifier)) {
          return false;
        }
        medicalCertificateCollectionIdentifiers.push(medicalCertificateIdentifier);
        return true;
      });
      return [...medicalCertificatesToAdd, ...medicalCertificateCollection];
    }
    return medicalCertificateCollection;
  }

  protected convertDateFromClient(medicalCertificate: IMedicalCertificate): IMedicalCertificate {
    return Object.assign({}, medicalCertificate, {
      emissionDate: medicalCertificate.emissionDate?.isValid() ? medicalCertificate.emissionDate.toJSON() : undefined,
      attentionDate: medicalCertificate.attentionDate?.isValid() ? medicalCertificate.attentionDate.toJSON() : undefined,
      fromDate: medicalCertificate.fromDate?.isValid() ? medicalCertificate.fromDate.toJSON() : undefined,
      untilDate: medicalCertificate.untilDate?.isValid() ? medicalCertificate.untilDate.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.emissionDate = res.body.emissionDate ? dayjs(res.body.emissionDate) : undefined;
      res.body.attentionDate = res.body.attentionDate ? dayjs(res.body.attentionDate) : undefined;
      res.body.fromDate = res.body.fromDate ? dayjs(res.body.fromDate) : undefined;
      res.body.untilDate = res.body.untilDate ? dayjs(res.body.untilDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((medicalCertificate: IMedicalCertificate) => {
        medicalCertificate.emissionDate = medicalCertificate.emissionDate ? dayjs(medicalCertificate.emissionDate) : undefined;
        medicalCertificate.attentionDate = medicalCertificate.attentionDate ? dayjs(medicalCertificate.attentionDate) : undefined;
        medicalCertificate.fromDate = medicalCertificate.fromDate ? dayjs(medicalCertificate.fromDate) : undefined;
        medicalCertificate.untilDate = medicalCertificate.untilDate ? dayjs(medicalCertificate.untilDate) : undefined;
      });
    }
    return res;
  }
}
