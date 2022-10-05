import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class LoadingService {
  private loading = false;

  wrapper(callback: Observable<any>): Observable<any> {
    return new Observable(observer => {
      this.loading = true;
      callback
        .subscribe(
          resp => observer.next(resp),
          error => observer.error(error)
        )
        .add(() => (this.loading = false));
    });
  }

  isLoading(): boolean {
    return this.loading;
  }
}
