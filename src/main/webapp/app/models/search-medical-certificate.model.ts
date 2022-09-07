export interface ISearchMedicalCertificate {
  query?: string | null;
}

export class SearchMedicalCertificate implements ISearchMedicalCertificate {
  constructor(public query?: string | null) {}
}
