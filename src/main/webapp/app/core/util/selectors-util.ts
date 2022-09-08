import { User } from 'app/models/user-management.model';

export const patientLabel = (p: User): string => `${p.dni!} - ${p.firstName!} ${p.lastName!}`;
