export interface IInternationalDisease {
  id?: number;
  cod?: string | null;
  description?: string | null;
}

export class InternationalDisease implements IInternationalDisease {
  constructor(public id?: number, public cod?: string | null, public description?: string | null) {}
}
