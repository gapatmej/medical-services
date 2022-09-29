package com.customsoftware.medicalservices.repository;

import com.customsoftware.medicalservices.domain.InternationalDisease;
import com.customsoftware.medicalservices.domain.Organization;
import com.customsoftware.medicalservices.repository.custom.CustomInternationalDiseaseRepository;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InternationalDiseaseRepository extends JpaRepository<InternationalDisease, Long>, CustomInternationalDiseaseRepository {
    @Query(" select o from Organization o ")
    List<Organization> getOrganizations(Pageable pageable);
}
