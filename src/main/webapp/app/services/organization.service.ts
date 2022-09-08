import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IOrganization } from '../models/organization.model';

export type EntityResponseType = HttpResponse<IOrganization>;

@Injectable({ providedIn: 'root' })
export class OrganizationService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/organization');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  save(organization: IOrganization): Observable<EntityResponseType> {
    return this.http.post<IOrganization>(this.resourceUrl, organization, { observe: 'response' });
  }

  getOrganization(): Observable<EntityResponseType> {
    return this.http.get<IOrganization>(this.resourceUrl, { observe: 'response' });
  }
}
