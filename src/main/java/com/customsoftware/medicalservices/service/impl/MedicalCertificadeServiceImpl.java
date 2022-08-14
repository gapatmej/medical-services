package com.customsoftware.medicalservices.service.impl;

import com.customsoftware.medicalservices.domain.MedicalCertificade;
import com.customsoftware.medicalservices.repository.MedicalCertificadeRepository;
import com.customsoftware.medicalservices.service.MedicalCertificadeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MedicalCertificade}.
 */
@Service
@Transactional
public class MedicalCertificadeServiceImpl implements MedicalCertificadeService {

    private final Logger log = LoggerFactory.getLogger(MedicalCertificadeServiceImpl.class);

    private final MedicalCertificadeRepository medicalCertificadeRepository;

    public MedicalCertificadeServiceImpl(MedicalCertificadeRepository medicalCertificadeRepository) {
        this.medicalCertificadeRepository = medicalCertificadeRepository;
    }

    @Override
    public MedicalCertificade save(MedicalCertificade medicalCertificade) {
        log.debug("Request to save MedicalCertificade : {}", medicalCertificade);
        return medicalCertificadeRepository.save(medicalCertificade);
    }

    @Override
    public Optional<MedicalCertificade> partialUpdate(MedicalCertificade medicalCertificade) {
        log.debug("Request to partially update MedicalCertificade : {}", medicalCertificade);

        return medicalCertificadeRepository
            .findById(medicalCertificade.getId())
            .map(
                existingMedicalCertificade -> {
                    if (medicalCertificade.getEmissionDate() != null) {
                        existingMedicalCertificade.setEmissionDate(medicalCertificade.getEmissionDate());
                    }
                    if (medicalCertificade.getFirstName() != null) {
                        existingMedicalCertificade.setFirstName(medicalCertificade.getFirstName());
                    }
                    if (medicalCertificade.getLastName() != null) {
                        existingMedicalCertificade.setLastName(medicalCertificade.getLastName());
                    }
                    if (medicalCertificade.getAddress() != null) {
                        existingMedicalCertificade.setAddress(medicalCertificade.getAddress());
                    }
                    if (medicalCertificade.getClinicHistoryNumber() != null) {
                        existingMedicalCertificade.setClinicHistoryNumber(medicalCertificade.getClinicHistoryNumber());
                    }
                    if (medicalCertificade.getIdentificationType() != null) {
                        existingMedicalCertificade.setIdentificationType(medicalCertificade.getIdentificationType());
                    }
                    if (medicalCertificade.getIdentification() != null) {
                        existingMedicalCertificade.setIdentification(medicalCertificade.getIdentification());
                    }
                    if (medicalCertificade.getPhone() != null) {
                        existingMedicalCertificade.setPhone(medicalCertificade.getPhone());
                    }
                    if (medicalCertificade.getMobilePhone() != null) {
                        existingMedicalCertificade.setMobilePhone(medicalCertificade.getMobilePhone());
                    }
                    if (medicalCertificade.getAttentionDate() != null) {
                        existingMedicalCertificade.setAttentionDate(medicalCertificade.getAttentionDate());
                    }
                    if (medicalCertificade.getDiagnosis() != null) {
                        existingMedicalCertificade.setDiagnosis(medicalCertificade.getDiagnosis());
                    }
                    if (medicalCertificade.getRestType() != null) {
                        existingMedicalCertificade.setRestType(medicalCertificade.getRestType());
                    }
                    if (medicalCertificade.getFromDate() != null) {
                        existingMedicalCertificade.setFromDate(medicalCertificade.getFromDate());
                    }
                    if (medicalCertificade.getUntilDate() != null) {
                        existingMedicalCertificade.setUntilDate(medicalCertificade.getUntilDate());
                    }
                    if (medicalCertificade.getTotal() != null) {
                        existingMedicalCertificade.setTotal(medicalCertificade.getTotal());
                    }
                    if (medicalCertificade.getObservation() != null) {
                        existingMedicalCertificade.setObservation(medicalCertificade.getObservation());
                    }
                    if (medicalCertificade.getSymptom() != null) {
                        existingMedicalCertificade.setSymptom(medicalCertificade.getSymptom());
                    }

                    return existingMedicalCertificade;
                }
            )
            .map(medicalCertificadeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalCertificade> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalCertificades");
        return medicalCertificadeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalCertificade> findOne(Long id) {
        log.debug("Request to get MedicalCertificade : {}", id);
        return medicalCertificadeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalCertificade : {}", id);
        medicalCertificadeRepository.deleteById(id);
    }
}
