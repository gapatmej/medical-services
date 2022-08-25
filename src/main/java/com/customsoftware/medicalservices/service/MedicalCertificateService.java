package com.customsoftware.medicalservices.service;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MedicalCertificate}.
 */
public interface MedicalCertificateService {
    /**
     * Save a medicalCertificate.
     *
     * @param medicalCertificate the entity to save.
     * @return the persisted entity.
     */
    MedicalCertificate save(MedicalCertificate medicalCertificate);

    /**
     * Partially updates a medicalCertificate.
     *
     * @param medicalCertificate the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MedicalCertificate> partialUpdate(MedicalCertificate medicalCertificate);

    /**
     * Get all the medicalCertificates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicalCertificate> findAll(Pageable pageable);

    /**
     * Get the "id" medicalCertificate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalCertificate> findOne(Long id);

    /**
     * Delete the "id" medicalCertificate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
