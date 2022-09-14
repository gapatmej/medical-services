import { User } from 'app/models/user-management.model';

export const userLabel = (p: User): string => `${p.dni!} - ${p.firstName!} ${p.lastName!}`;
