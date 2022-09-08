import { User } from 'app/models/user-management.model';
import * as dayjs from 'dayjs';

export interface IMedicalCertificate {
  id?: number;
  doctor?: User;
  patient?: User | null;
  emissionDate?: dayjs.Dayjs | null;
  emissionPlace?: string | null;
  diagnosis?: string | null;
  cie10Cod?: string | null;
  symptoms?: boolean;
  disease?: boolean;
  diseaseDescription?: string | null;
  insulation?: boolean;
  insulationDescription?: string | null;
  totalDaysOff?: number;
  fromDate?: dayjs.Dayjs | null;
  untilDate?: dayjs.Dayjs | null;
  status?: string | null;
}

export class MedicalCertificate implements IMedicalCertificate {
  constructor(
    public id?: number,
    public doctor?: User,
    public patient?: User | null,
    public emissionDate?: dayjs.Dayjs | null,
    public emissionPlace?: string | null,
    public diagnosis?: string | null,
    public cie10Cod?: string | null,
    public symptoms?: boolean,
    public disease?: boolean,
    public diseaseDescription?: string | null,
    public insulation?: boolean,
    public insulationDescription?: string | null,
    public totalDaysOff?: number,
    public fromDate?: dayjs.Dayjs | null,
    public untilDate?: dayjs.Dayjs | null,
    public status?: string | null
  ) {}
}
