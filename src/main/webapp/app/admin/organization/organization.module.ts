import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { organizationRoute } from './organization.route';
import { OrganizationUpdateComponent } from 'app/admin/organization/organization-update.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(organizationRoute)],
  declarations: [OrganizationUpdateComponent],
})
export class OrganizationModule {}
