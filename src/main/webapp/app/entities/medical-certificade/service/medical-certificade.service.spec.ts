import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IdentificationType } from 'app/entities/enumerations/identification-type.model';
import { IMedicalCertificade, MedicalCertificade } from '../medical-certificade.model';

import { MedicalCertificadeService } from './medical-certificade.service';

describe('Service Tests', () => {
  describe('MedicalCertificade Service', () => {
    let service: MedicalCertificadeService;
    let httpMock: HttpTestingController;
    let elemDefault: IMedicalCertificade;
    let expectedResult: IMedicalCertificade | IMedicalCertificade[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(MedicalCertificadeService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        emissionDate: currentDate,
        firstName: 'AAAAAAA',
        lastName: 'AAAAAAA',
        address: 'AAAAAAA',
        clinicHistoryNumber: 'AAAAAAA',
        identificationType: IdentificationType.DNI,
        identification: 'AAAAAAA',
        phone: 'AAAAAAA',
        mobilePhone: 'AAAAAAA',
        attentionDate: currentDate,
        diagnosis: 'AAAAAAA',
        restType: 'AAAAAAA',
        fromDate: currentDate,
        untilDate: currentDate,
        total: 0,
        observation: 'AAAAAAA',
        symptom: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            emissionDate: currentDate.format(DATE_TIME_FORMAT),
            attentionDate: currentDate.format(DATE_TIME_FORMAT),
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            untilDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MedicalCertificade', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            emissionDate: currentDate.format(DATE_TIME_FORMAT),
            attentionDate: currentDate.format(DATE_TIME_FORMAT),
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            untilDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            emissionDate: currentDate,
            attentionDate: currentDate,
            fromDate: currentDate,
            untilDate: currentDate,
          },
          returnedFromService
        );

        service.create(new MedicalCertificade()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MedicalCertificade', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            emissionDate: currentDate.format(DATE_TIME_FORMAT),
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            address: 'BBBBBB',
            clinicHistoryNumber: 'BBBBBB',
            identificationType: 'BBBBBB',
            identification: 'BBBBBB',
            phone: 'BBBBBB',
            mobilePhone: 'BBBBBB',
            attentionDate: currentDate.format(DATE_TIME_FORMAT),
            diagnosis: 'BBBBBB',
            restType: 'BBBBBB',
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            untilDate: currentDate.format(DATE_TIME_FORMAT),
            total: 1,
            observation: 'BBBBBB',
            symptom: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            emissionDate: currentDate,
            attentionDate: currentDate,
            fromDate: currentDate,
            untilDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a MedicalCertificade', () => {
        const patchObject = Object.assign(
          {
            firstName: 'BBBBBB',
            clinicHistoryNumber: 'BBBBBB',
            mobilePhone: 'BBBBBB',
            attentionDate: currentDate.format(DATE_TIME_FORMAT),
            restType: 'BBBBBB',
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            untilDate: currentDate.format(DATE_TIME_FORMAT),
            total: 1,
            observation: 'BBBBBB',
          },
          new MedicalCertificade()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            emissionDate: currentDate,
            attentionDate: currentDate,
            fromDate: currentDate,
            untilDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MedicalCertificade', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            emissionDate: currentDate.format(DATE_TIME_FORMAT),
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            address: 'BBBBBB',
            clinicHistoryNumber: 'BBBBBB',
            identificationType: 'BBBBBB',
            identification: 'BBBBBB',
            phone: 'BBBBBB',
            mobilePhone: 'BBBBBB',
            attentionDate: currentDate.format(DATE_TIME_FORMAT),
            diagnosis: 'BBBBBB',
            restType: 'BBBBBB',
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            untilDate: currentDate.format(DATE_TIME_FORMAT),
            total: 1,
            observation: 'BBBBBB',
            symptom: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            emissionDate: currentDate,
            attentionDate: currentDate,
            fromDate: currentDate,
            untilDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MedicalCertificade', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMedicalCertificadeToCollectionIfMissing', () => {
        it('should add a MedicalCertificade to an empty array', () => {
          const medicalCertificade: IMedicalCertificade = { id: 123 };
          expectedResult = service.addMedicalCertificadeToCollectionIfMissing([], medicalCertificade);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(medicalCertificade);
        });

        it('should not add a MedicalCertificade to an array that contains it', () => {
          const medicalCertificade: IMedicalCertificade = { id: 123 };
          const medicalCertificadeCollection: IMedicalCertificade[] = [
            {
              ...medicalCertificade,
            },
            { id: 456 },
          ];
          expectedResult = service.addMedicalCertificadeToCollectionIfMissing(medicalCertificadeCollection, medicalCertificade);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a MedicalCertificade to an array that doesn't contain it", () => {
          const medicalCertificade: IMedicalCertificade = { id: 123 };
          const medicalCertificadeCollection: IMedicalCertificade[] = [{ id: 456 }];
          expectedResult = service.addMedicalCertificadeToCollectionIfMissing(medicalCertificadeCollection, medicalCertificade);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(medicalCertificade);
        });

        it('should add only unique MedicalCertificade to an array', () => {
          const medicalCertificadeArray: IMedicalCertificade[] = [{ id: 123 }, { id: 456 }, { id: 39230 }];
          const medicalCertificadeCollection: IMedicalCertificade[] = [{ id: 123 }];
          expectedResult = service.addMedicalCertificadeToCollectionIfMissing(medicalCertificadeCollection, ...medicalCertificadeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const medicalCertificade: IMedicalCertificade = { id: 123 };
          const medicalCertificade2: IMedicalCertificade = { id: 456 };
          expectedResult = service.addMedicalCertificadeToCollectionIfMissing([], medicalCertificade, medicalCertificade2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(medicalCertificade);
          expect(expectedResult).toContain(medicalCertificade2);
        });

        it('should accept null and undefined values', () => {
          const medicalCertificade: IMedicalCertificade = { id: 123 };
          expectedResult = service.addMedicalCertificadeToCollectionIfMissing([], null, medicalCertificade, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(medicalCertificade);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
