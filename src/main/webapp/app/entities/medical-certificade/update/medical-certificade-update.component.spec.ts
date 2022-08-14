jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MedicalCertificadeService } from '../service/medical-certificade.service';
import { IMedicalCertificade, MedicalCertificade } from '../medical-certificade.model';

import { MedicalCertificadeUpdateComponent } from './medical-certificade-update.component';

describe('Component Tests', () => {
  describe('MedicalCertificade Management Update Component', () => {
    let comp: MedicalCertificadeUpdateComponent;
    let fixture: ComponentFixture<MedicalCertificadeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let medicalCertificadeService: MedicalCertificadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MedicalCertificadeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(MedicalCertificadeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MedicalCertificadeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      medicalCertificadeService = TestBed.inject(MedicalCertificadeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const medicalCertificade: IMedicalCertificade = { id: 456 };

        activatedRoute.data = of({ medicalCertificade });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(medicalCertificade));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const medicalCertificade = { id: 123 };
        spyOn(medicalCertificadeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ medicalCertificade });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: medicalCertificade }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(medicalCertificadeService.update).toHaveBeenCalledWith(medicalCertificade);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const medicalCertificade = new MedicalCertificade();
        spyOn(medicalCertificadeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ medicalCertificade });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: medicalCertificade }));
        saveSubject.complete();

        // THEN
        expect(medicalCertificadeService.create).toHaveBeenCalledWith(medicalCertificade);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const medicalCertificade = { id: 123 };
        spyOn(medicalCertificadeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ medicalCertificade });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(medicalCertificadeService.update).toHaveBeenCalledWith(medicalCertificade);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
