import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';

export interface IPagination {
  page: number;
  size: number;
  sort?: string[];
}

export class Pagination implements IPagination {
  constructor(public page = 0, public size = ITEMS_PER_PAGE, public sort?: string[]) {}
}
