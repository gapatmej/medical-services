package com.customsoftware.medicalservices.repository;

import com.customsoftware.medicalservices.domain.MedicalCertificade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MedicalCertificade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalCertificadeRepository extends JpaRepository<MedicalCertificade, Long> {}
