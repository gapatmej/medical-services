import { IdentificationType } from 'app/models/enumeration/identification-type.model';

export const getAutorityName = (authority: string): string => `global.authorities.${authority}`;

export const getIdentifycationTypeName = (identificationType: IdentificationType): string =>
  `userManagement.identificationType.names.${identificationType}`;
