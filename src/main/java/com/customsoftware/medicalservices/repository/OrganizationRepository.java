package com.customsoftware.medicalservices.repository;

import com.customsoftware.medicalservices.domain.Organization;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    @Query(" select o from Organization o ")
    List<Organization> getOrganizations(Pageable pageable);
}
