package com.customsoftware.medicalservices.repository;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.repository.custom.CustomMedicalCertificateRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface MedicalCertificateRepository extends JpaRepository<MedicalCertificate, Long>, CustomMedicalCertificateRepository {
    @Query(" select mC from MedicalCertificate mC join fetch mC.doctor join fetch mC.patient where mC.id = :id ")
    Optional<MedicalCertificate> searchById(@Param("id") Long id);

    @Query(
        " select mC from MedicalCertificate mC join fetch mC.doctor join fetch mC.patient where mC.id = :id and mC.doctor.login = :loginDoctor "
    )
    Optional<MedicalCertificate> searchByIdAndDoctor(@Param("id") Long id, @Param("loginDoctor") String loginDoctor);

    @Query(
        " select mC from MedicalCertificate mC join fetch mC.doctor join fetch mC.patient where mC.id = :id " +
        " and (mC.doctor.login = :login or mC.patient.login = :login) "
    )
    Optional<MedicalCertificate> searchByIdAndDoctorOrPatient(@Param("id") Long id, @Param("login") String login);
}
