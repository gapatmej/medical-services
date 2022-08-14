package com.customsoftware.medicalservices.service;

import com.customsoftware.medicalservices.domain.MedicalCertificade;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MedicalCertificade}.
 */
public interface MedicalCertificadeService {
    /**
     * Save a medicalCertificade.
     *
     * @param medicalCertificade the entity to save.
     * @return the persisted entity.
     */
    MedicalCertificade save(MedicalCertificade medicalCertificade);

    /**
     * Partially updates a medicalCertificade.
     *
     * @param medicalCertificade the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MedicalCertificade> partialUpdate(MedicalCertificade medicalCertificade);

    /**
     * Get all the medicalCertificades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicalCertificade> findAll(Pageable pageable);

    /**
     * Get the "id" medicalCertificade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalCertificade> findOne(Long id);

    /**
     * Delete the "id" medicalCertificade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
