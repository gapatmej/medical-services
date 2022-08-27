import { Authority } from 'app/config/authority.constants';

export interface ISearchUser {
  query?: string | null;
  roles?: Authority[];
}

export class SearchUser implements ISearchUser {
  constructor(public query?: string | null, public roles?: Authority[]) {}
}
