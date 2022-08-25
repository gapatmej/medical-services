package com.customsoftware.medicalservices.service.impl;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.repository.MedicalCertificateRepository;
import com.customsoftware.medicalservices.service.MedicalCertificateService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MedicalCertificate}.
 */
@Service
@Transactional
public class MedicalCertificateServiceImpl implements MedicalCertificateService {

    private final Logger log = LoggerFactory.getLogger(MedicalCertificateServiceImpl.class);

    private final MedicalCertificateRepository medicalCertificateRepository;

    public MedicalCertificateServiceImpl(MedicalCertificateRepository medicalCertificateRepository) {
        this.medicalCertificateRepository = medicalCertificateRepository;
    }

    @Override
    public MedicalCertificate save(MedicalCertificate medicalCertificate) {
        log.debug("Request to save MedicalCertificate : {}", medicalCertificate);
        return medicalCertificateRepository.save(medicalCertificate);
    }

    @Override
    public Optional<MedicalCertificate> partialUpdate(MedicalCertificate medicalCertificate) {
        log.debug("Request to partially update MedicalCertificate : {}", medicalCertificate);

        return medicalCertificateRepository
            .findById(medicalCertificate.getId())
            .map(
                existingMedicalCertificate -> {
                    if (medicalCertificate.getEmissionDate() != null) {
                        existingMedicalCertificate.setEmissionDate(medicalCertificate.getEmissionDate());
                    }
                    if (medicalCertificate.getFirstName() != null) {
                        existingMedicalCertificate.setFirstName(medicalCertificate.getFirstName());
                    }
                    if (medicalCertificate.getLastName() != null) {
                        existingMedicalCertificate.setLastName(medicalCertificate.getLastName());
                    }
                    if (medicalCertificate.getAddress() != null) {
                        existingMedicalCertificate.setAddress(medicalCertificate.getAddress());
                    }
                    if (medicalCertificate.getClinicHistoryNumber() != null) {
                        existingMedicalCertificate.setClinicHistoryNumber(medicalCertificate.getClinicHistoryNumber());
                    }
                    if (medicalCertificate.getIdentificationType() != null) {
                        existingMedicalCertificate.setIdentificationType(medicalCertificate.getIdentificationType());
                    }
                    if (medicalCertificate.getIdentification() != null) {
                        existingMedicalCertificate.setIdentification(medicalCertificate.getIdentification());
                    }
                    if (medicalCertificate.getPhone() != null) {
                        existingMedicalCertificate.setPhone(medicalCertificate.getPhone());
                    }
                    if (medicalCertificate.getMobilePhone() != null) {
                        existingMedicalCertificate.setMobilePhone(medicalCertificate.getMobilePhone());
                    }
                    if (medicalCertificate.getAttentionDate() != null) {
                        existingMedicalCertificate.setAttentionDate(medicalCertificate.getAttentionDate());
                    }
                    if (medicalCertificate.getDiagnosis() != null) {
                        existingMedicalCertificate.setDiagnosis(medicalCertificate.getDiagnosis());
                    }
                    if (medicalCertificate.getRestType() != null) {
                        existingMedicalCertificate.setRestType(medicalCertificate.getRestType());
                    }
                    if (medicalCertificate.getFromDate() != null) {
                        existingMedicalCertificate.setFromDate(medicalCertificate.getFromDate());
                    }
                    if (medicalCertificate.getUntilDate() != null) {
                        existingMedicalCertificate.setUntilDate(medicalCertificate.getUntilDate());
                    }
                    if (medicalCertificate.getTotal() != null) {
                        existingMedicalCertificate.setTotal(medicalCertificate.getTotal());
                    }
                    if (medicalCertificate.getObservation() != null) {
                        existingMedicalCertificate.setObservation(medicalCertificate.getObservation());
                    }
                    if (medicalCertificate.getSymptom() != null) {
                        existingMedicalCertificate.setSymptom(medicalCertificate.getSymptom());
                    }

                    return existingMedicalCertificate;
                }
            )
            .map(medicalCertificateRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalCertificate> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalCertificates");
        return medicalCertificateRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalCertificate> findOne(Long id) {
        log.debug("Request to get MedicalCertificate : {}", id);
        return medicalCertificateRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalCertificate : {}", id);
        medicalCertificateRepository.deleteById(id);
    }
}
