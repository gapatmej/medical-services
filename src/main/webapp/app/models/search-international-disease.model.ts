export interface ISearchInternationalDisease {
  query?: string | null;
}

export class SearchInternationalDisease implements ISearchInternationalDisease {
  constructor(public query?: string | null) {}
}
