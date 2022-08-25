import { Authority } from 'app/config/authority.constants';

export function getAutorityName(authority: string): string {
  switch (authority) {
    case Authority.ADMIN:
      return 'global.authorities.admin';
    case Authority.DOCTOR:
      return 'global.authorities.doctor';
    case Authority.PATIENT:
      return 'global.authorities.patient';
    default:
      return '';
  }
}
