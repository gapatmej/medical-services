import { User } from 'app/admin/user-management/user-management.model';

export const patientLabel = (p: User): string => `${p.dni!} - ${p.firstName!} ${p.lastName!}`;
