import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUser } from '../models/user-management.model';
import { ISearchUser } from 'app/models/search-user.model';
import { Pagination } from 'app/models/pagination.model';

@Injectable({ providedIn: 'root' })
export class UserManagementService {
  public resourceUrlAdmin = this.applicationConfigService.getEndpointFor('api/admin/users');
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/users');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(user: IUser): Observable<IUser> {
    return this.http.post<IUser>(this.resourceUrlAdmin, user);
  }

  update(user: IUser): Observable<IUser> {
    return this.http.put<IUser>(this.resourceUrlAdmin, user);
  }

  find(login: string): Observable<IUser> {
    return this.http.get<IUser>(`${this.resourceUrlAdmin}/${login}`);
  }

  query(req?: Pagination): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    return this.http.get<IUser[]>(this.resourceUrlAdmin, { params: options, observe: 'response' });
  }

  delete(login: string): Observable<{}> {
    return this.http.delete(`${this.resourceUrlAdmin}/${login}`);
  }

  authorities(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/authorities'));
  }

  search(pagination: Pagination, searchUser: ISearchUser): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(pagination);
    return this.http.post<IUser[]>(`${this.resourceUrl}/search`, searchUser, { params: options, observe: 'response' });
  }

  saveCertificate(certificate: FormData): Observable<{}> {
    return this.http.post(`${this.resourceUrl}/update-certificate`, certificate);
  }
}
