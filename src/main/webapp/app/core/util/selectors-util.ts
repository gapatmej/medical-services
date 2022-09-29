import { IInternationalDisease } from 'app/models/international-disease.model';
import { User } from 'app/models/user-management.model';

export const userLabel = (p: User): string => `${p.dni!} - ${p.firstName!} ${p.lastName!}`;

export const internationalDiseaseLabel = (iD: IInternationalDisease): string => `${iD.cod!} - ${iD.description!}`;
