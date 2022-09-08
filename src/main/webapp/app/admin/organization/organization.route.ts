import { Routes } from '@angular/router';
import { OrganizationUpdateComponent } from 'app/admin/organization/organization-update.component';

export const organizationRoute: Routes = [
  {
    path: '',
    component: OrganizationUpdateComponent,
    data: {},
  },
];
