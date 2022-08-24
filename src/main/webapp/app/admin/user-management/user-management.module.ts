import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { UserManagementComponent } from './list/user-management.component';
import { UserManagementUpdateComponent } from './update/user-management-update.component';
import { UserManagementDeleteDialogComponent } from './delete/user-management-delete-dialog.component';
import { userManagementRoute } from './user-management.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(userManagementRoute)],
  declarations: [UserManagementComponent, UserManagementUpdateComponent, UserManagementDeleteDialogComponent],
  entryComponents: [UserManagementDeleteDialogComponent],
})
export class UserManagementModule {}
