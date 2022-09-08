package com.customsoftware.medicalservices.service.impl;

import com.customsoftware.medicalservices.domain.Organization;
import com.customsoftware.medicalservices.repository.OrganizationRepository;
import com.customsoftware.medicalservices.service.OrganizationService;
import com.customsoftware.medicalservices.service.dto.OrganizationDTO;
import com.customsoftware.medicalservices.service.mapper.OrganizationMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrganizationServiceImpl extends AbstractServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper) {
        super(OrganizationServiceImpl.class);
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public OrganizationDTO save(OrganizationDTO organizationDTO) {
        Organization organization = organizationMapper.toEntity(organizationDTO);
        getFirstOrganization().ifPresent(o -> organization.setId(o.getId()));
        return organizationMapper.toDto(organizationRepository.save(organization));
    }

    @Override
    public OrganizationDTO getOrganization() {
        return getFirstOrganization().map(organizationMapper::toDto).orElse(new OrganizationDTO());
    }

    private Optional<Organization> getFirstOrganization() {
        List<Organization> organizations = organizationRepository.getOrganizations(Pageable.unpaged().first());

        if (!organizations.isEmpty()) {
            return Optional.of(organizations.get(0));
        }

        return Optional.empty();
    }
}
