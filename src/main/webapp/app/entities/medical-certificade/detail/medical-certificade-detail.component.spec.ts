import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MedicalCertificadeDetailComponent } from './medical-certificade-detail.component';

describe('Component Tests', () => {
  describe('MedicalCertificade Management Detail Component', () => {
    let comp: MedicalCertificadeDetailComponent;
    let fixture: ComponentFixture<MedicalCertificadeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [MedicalCertificadeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ medicalCertificade: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(MedicalCertificadeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MedicalCertificadeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load medicalCertificade on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.medicalCertificade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
