import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITEMS_PER_PAGE, MAX_SIZE } from 'app/config/pagination.constants';
import { AccountService } from 'app/services/account.service';
import { Account } from 'app/models/account.model';
import { UserManagementService } from 'app/services/user-management.service';
import { User } from 'app/models/user-management.model';
import { UserManagementDeleteDialogComponent } from 'app/admin/user-management/delete/user-management-delete-dialog.component';
import { Pagination } from 'app/models/pagination.model';
import { SearchUser } from 'app/models/search-user.model';
import { getAutorityName, getIdentifycationTypeName } from 'app/core/util/enumeration-util';
import { IdentificationType } from 'app/models/enumeration/identification-type.model';

@Component({
  selector: 'jhi-user-mgmt',
  templateUrl: './user-management.component.html',
})
export class UserManagementComponent implements OnInit {
  currentAccount: Account | null = null;
  users: User[] | null = null;
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  maxSize = MAX_SIZE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  textToSearch = '';
  searchUser = new SearchUser();

  constructor(
    private userService: UserManagementService,
    private accountService: AccountService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.currentAccount = account));
    this.handleNavigation();
  }

  identificationTypeName = (i: IdentificationType): string => getIdentifycationTypeName(i);

  autorityName = (i: string): string => getAutorityName(i);

  setActive(user: User, isActivated: boolean): void {
    this.userService.update({ ...user, activated: isActivated }).subscribe(() => this.loadAll());
  }

  deleteUser(user: User): void {
    const modalRef = this.modalService.open(UserManagementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.user = user;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  cleanSearchText(): void {
    this.textToSearch = '';
    this.onSearchUser();
  }

  onSearchUser(): void {
    console.log('a', this.textToSearch);
    this.searchUser.query = this.textToSearch;
    this.loadAll();
  }

  loadAll(): void {
    this.isLoading = true;
    this.userService
      .searchByAdmin(
        {
          ...new Pagination(),
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        },
        this.searchUser
      )
      .subscribe(
        (res: HttpResponse<User[]>) => {
          this.isLoading = false;
          this.onSuccess(res.body, res.headers);
        },
        () => (this.isLoading = false)
      );
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
      },
    });
  }

  private handleNavigation(): void {
    combineLatest([this.activatedRoute.data, this.activatedRoute.queryParamMap]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.page = page ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === 'asc';
      this.loadAll();
    });
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    return result;
  }

  private onSuccess(users: User[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.users = users;
  }
}
