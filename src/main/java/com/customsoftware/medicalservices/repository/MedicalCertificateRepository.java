package com.customsoftware.medicalservices.repository;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.domain.MedicalCertificate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MedicalCertificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalCertificateRepository extends JpaRepository<MedicalCertificate, Long> {}
