package com.customsoftware.medicalservices.repository.custom.impl;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.repository.custom.CustomMedicalCertificateRepository;
import com.customsoftware.medicalservices.service.dto.search.SearchMedicalCertificateDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class CustomMedicalCertificateRepositoryImpl implements CustomMedicalCertificateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<MedicalCertificate> search(SearchMedicalCertificateDTO searchMedicalCertificateDTO, Pageable pageable) {
        StringBuilder sqlCount = new StringBuilder(" select count(distinct mC.id ) from MedicalCertificate mC ");
        StringBuilder sqlSelect = new StringBuilder(" select mC from MedicalCertificate mC ");
        sqlSelect.append(" join fetch mC.doctor d join fetch mC.patient p");
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1=1 ");

        if (!StringUtils.isBlank(searchMedicalCertificateDTO.getQuery())) {
            sql.append(" and p.firstName like CONCAT('%',:query,'%') or p.lastName like CONCAT('%',:query,'%')");
        }

        if (!StringUtils.isBlank(searchMedicalCertificateDTO.getDoctorLogin())) {
            sql.append(" and mC.doctor.login = :doctorLogin");
        }

        if (!StringUtils.isBlank(searchMedicalCertificateDTO.getPatientLogin())) {
            sql.append(" and mC.patient.login = :patientLogin");
        }

        sqlSelect.append(sql);
        sqlCount.append(sql);

        Query queryCount = entityManager.createQuery(sqlCount.toString());
        Query querySelect = entityManager.createQuery(sqlSelect.toString());

        if (!StringUtils.isBlank(searchMedicalCertificateDTO.getQuery())) {
            queryCount.setParameter("query", searchMedicalCertificateDTO.getQuery());
            querySelect.setParameter("query", searchMedicalCertificateDTO.getQuery());
        }

        if (!StringUtils.isBlank(searchMedicalCertificateDTO.getDoctorLogin())) {
            queryCount.setParameter("doctorLogin", searchMedicalCertificateDTO.getDoctorLogin());
            querySelect.setParameter("doctorLogin", searchMedicalCertificateDTO.getDoctorLogin());
        }

        if (!StringUtils.isBlank(searchMedicalCertificateDTO.getPatientLogin())) {
            queryCount.setParameter("patientLogin", searchMedicalCertificateDTO.getPatientLogin());
            querySelect.setParameter("patientLogin", searchMedicalCertificateDTO.getPatientLogin());
        }

        querySelect.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize());
        querySelect.setMaxResults(pageable.getPageSize());

        List<MedicalCertificate> resultList = querySelect.getResultList();
        Long total = (Long) queryCount.getSingleResult();
        return new PageImpl<>(resultList, pageable, total);
    }
}
