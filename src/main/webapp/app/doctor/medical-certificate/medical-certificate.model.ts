import { IdentificationType } from 'app/config/enumeration/identification-type.model';
import * as dayjs from 'dayjs';

export interface IMedicalCertificate {
  id?: number;
  emissionDate?: dayjs.Dayjs | null;
  firstName?: string | null;
  lastName?: string | null;
  address?: string | null;
  clinicHistoryNumber?: string | null;
  identificationType?: IdentificationType | null;
  identification?: string | null;
  phone?: string | null;
  mobilePhone?: string | null;
  attentionDate?: dayjs.Dayjs | null;
  diagnosis?: string | null;
  restType?: string | null;
  fromDate?: dayjs.Dayjs | null;
  untilDate?: dayjs.Dayjs | null;
  total?: number | null;
  observation?: string | null;
  symptom?: string | null;
}

export class MedicalCertificate implements IMedicalCertificate {
  constructor(
    public id?: number,
    public emissionDate?: dayjs.Dayjs | null,
    public firstName?: string | null,
    public lastName?: string | null,
    public address?: string | null,
    public clinicHistoryNumber?: string | null,
    public identificationType?: IdentificationType | null,
    public identification?: string | null,
    public phone?: string | null,
    public mobilePhone?: string | null,
    public attentionDate?: dayjs.Dayjs | null,
    public diagnosis?: string | null,
    public restType?: string | null,
    public fromDate?: dayjs.Dayjs | null,
    public untilDate?: dayjs.Dayjs | null,
    public total?: number | null,
    public observation?: string | null,
    public symptom?: string | null
  ) {}
}

export function getMedicalCertificateIdentifier(medicalCertificate: IMedicalCertificate): number | undefined {
  return medicalCertificate.id;
}
