import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';

import { User, IUser } from '../../models/user-management.model';
import { UserManagementService } from 'app/services/user-management.service';
import { UserManagementComponent } from 'app/admin/user-management/user-management.component';
import { UserManagementUpdateComponent } from './update/user-management-update.component';

@Injectable({ providedIn: 'root' })
export class UserManagementResolve implements Resolve<IUser> {
  constructor(private service: UserManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUser> {
    const id = route.params['login'];
    if (id) {
      return this.service.find(id);
    }
    return of(new User());
  }
}

export const userManagementRoute: Routes = [
  {
    path: '',
    component: UserManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: 'new',
    component: UserManagementUpdateComponent,
    resolve: {
      user: UserManagementResolve,
    },
  },
  {
    path: ':login/edit',
    component: UserManagementUpdateComponent,
    resolve: {
      user: UserManagementResolve,
    },
  },
];
