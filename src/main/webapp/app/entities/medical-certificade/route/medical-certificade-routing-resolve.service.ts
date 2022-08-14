import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IMedicalCertificade, MedicalCertificade } from '../medical-certificade.model';
import { MedicalCertificadeService } from '../service/medical-certificade.service';

@Injectable({ providedIn: 'root' })
export class MedicalCertificadeRoutingResolveService implements Resolve<IMedicalCertificade> {
  constructor(protected service: MedicalCertificadeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMedicalCertificade> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((medicalCertificade: HttpResponse<MedicalCertificade>) => {
          if (medicalCertificade.body) {
            return of(medicalCertificade.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MedicalCertificade());
  }
}
