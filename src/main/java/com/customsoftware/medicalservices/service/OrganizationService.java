package com.customsoftware.medicalservices.service;

import com.customsoftware.medicalservices.service.dto.OrganizationDTO;
import java.util.Optional;

public interface OrganizationService {
    OrganizationDTO save(OrganizationDTO organizationDTO);

    OrganizationDTO getOrganization();
}
