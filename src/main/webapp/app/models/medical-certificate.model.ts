import { IInternationalDisease } from 'app/models/international-disease.model';
import { ContingencyType } from 'app/models/enumeration/contingency-type.model';
import { User } from 'app/models/user-management.model';
import * as dayjs from 'dayjs';

export interface IMedicalCertificate {
  id?: number;
  doctor?: User;
  patient?: User | null;
  emissionDate?: dayjs.Dayjs | null;
  diagnosis?: string | null;
  internationalDisease?: IInternationalDisease | null;
  symptoms?: boolean;
  disease?: boolean;
  diseaseDescription?: string | null;
  insulation?: boolean;
  insulationDescription?: string | null;
  fromDate?: dayjs.Dayjs | null;
  untilDate?: dayjs.Dayjs | null;
  status?: string | null;
  contingencyType?: ContingencyType;
}

export class MedicalCertificate implements IMedicalCertificate {
  constructor(
    public id?: number,
    public doctor?: User,
    public patient?: User | null,
    public emissionDate?: dayjs.Dayjs | null,
    public diagnosis?: string | null,
    public internationalDisease?: IInternationalDisease | null,
    public symptoms?: boolean,
    public disease?: boolean,
    public diseaseDescription?: string | null,
    public insulation?: boolean,
    public insulationDescription?: string | null,
    public fromDate?: dayjs.Dayjs | null,
    public untilDate?: dayjs.Dayjs | null,
    public status?: string | null,
    public contingencyType?: ContingencyType
  ) {}
}
