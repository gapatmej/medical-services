export interface IOrganization {
  id?: number;
  name?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  address?: string | null;
}

export class Organization implements IOrganization {
  constructor(
    public id?: number,
    public name?: string | null,
    public email?: string | null,
    public phoneNumber?: string | null,
    public address?: string | null
  ) {}
}
