import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMedicalCertificade, getMedicalCertificadeIdentifier } from '../medical-certificade.model';

export type EntityResponseType = HttpResponse<IMedicalCertificade>;
export type EntityArrayResponseType = HttpResponse<IMedicalCertificade[]>;

@Injectable({ providedIn: 'root' })
export class MedicalCertificadeService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/medical-certificades');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(medicalCertificade: IMedicalCertificade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalCertificade);
    return this.http
      .post<IMedicalCertificade>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicalCertificade: IMedicalCertificade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalCertificade);
    return this.http
      .put<IMedicalCertificade>(`${this.resourceUrl}/${getMedicalCertificadeIdentifier(medicalCertificade) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(medicalCertificade: IMedicalCertificade): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalCertificade);
    return this.http
      .patch<IMedicalCertificade>(`${this.resourceUrl}/${getMedicalCertificadeIdentifier(medicalCertificade) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicalCertificade>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicalCertificade[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMedicalCertificadeToCollectionIfMissing(
    medicalCertificadeCollection: IMedicalCertificade[],
    ...medicalCertificadesToCheck: (IMedicalCertificade | null | undefined)[]
  ): IMedicalCertificade[] {
    const medicalCertificades: IMedicalCertificade[] = medicalCertificadesToCheck.filter(isPresent);
    if (medicalCertificades.length > 0) {
      const medicalCertificadeCollectionIdentifiers = medicalCertificadeCollection.map(
        medicalCertificadeItem => getMedicalCertificadeIdentifier(medicalCertificadeItem)!
      );
      const medicalCertificadesToAdd = medicalCertificades.filter(medicalCertificadeItem => {
        const medicalCertificadeIdentifier = getMedicalCertificadeIdentifier(medicalCertificadeItem);
        if (medicalCertificadeIdentifier == null || medicalCertificadeCollectionIdentifiers.includes(medicalCertificadeIdentifier)) {
          return false;
        }
        medicalCertificadeCollectionIdentifiers.push(medicalCertificadeIdentifier);
        return true;
      });
      return [...medicalCertificadesToAdd, ...medicalCertificadeCollection];
    }
    return medicalCertificadeCollection;
  }

  protected convertDateFromClient(medicalCertificade: IMedicalCertificade): IMedicalCertificade {
    return Object.assign({}, medicalCertificade, {
      emissionDate: medicalCertificade.emissionDate?.isValid() ? medicalCertificade.emissionDate.toJSON() : undefined,
      attentionDate: medicalCertificade.attentionDate?.isValid() ? medicalCertificade.attentionDate.toJSON() : undefined,
      fromDate: medicalCertificade.fromDate?.isValid() ? medicalCertificade.fromDate.toJSON() : undefined,
      untilDate: medicalCertificade.untilDate?.isValid() ? medicalCertificade.untilDate.toJSON() : undefined,
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
      res.body.forEach((medicalCertificade: IMedicalCertificade) => {
        medicalCertificade.emissionDate = medicalCertificade.emissionDate ? dayjs(medicalCertificade.emissionDate) : undefined;
        medicalCertificade.attentionDate = medicalCertificade.attentionDate ? dayjs(medicalCertificade.attentionDate) : undefined;
        medicalCertificade.fromDate = medicalCertificade.fromDate ? dayjs(medicalCertificade.fromDate) : undefined;
        medicalCertificade.untilDate = medicalCertificade.untilDate ? dayjs(medicalCertificade.untilDate) : undefined;
      });
    }
    return res;
  }
}
