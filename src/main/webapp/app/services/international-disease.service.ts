import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Pagination } from 'app/models/pagination.model';
import { ISearchInternationalDisease } from 'app/models/search-international-disease.model';
import { IInternationalDisease } from 'app/models/international-disease.model';

export type EntityArrayResponseType = HttpResponse<IInternationalDisease[]>;

@Injectable({ providedIn: 'root' })
export class InternationalDiseaseService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/international-disease');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  search(pagination: Pagination, searchInternationalDisease: ISearchInternationalDisease): Observable<EntityArrayResponseType> {
    const options = createRequestOption(pagination);
    return this.http.post<IInternationalDisease[]>(`${this.resourceUrl}/search`, searchInternationalDisease, {
      params: options,
      observe: 'response',
    });
  }
}
