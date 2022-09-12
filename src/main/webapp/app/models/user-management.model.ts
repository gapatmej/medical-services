import { IdentificationType } from 'app/models/enumeration/identification-type.model';

export interface IUser {
  id?: number;
  login?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  address?: string;
  contactPhoneNumber?: string;
  occupation?: string;
  dni?: string;
  identificationType?: IdentificationType;
  medicalHistoryNumber?: string;
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
}

export class User implements IUser {
  constructor(
    public id?: number,
    public login?: string,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public address?: string,
    public contactPhoneNumber?: string,
    public occupation?: string,
    public dni?: string,
    public identificationType?: IdentificationType,
    public medicalHistoryNumber?: string,
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date
  ) {}
}
