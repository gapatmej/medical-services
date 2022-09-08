package com.customsoftware.medicalservices.service.mapper;

import com.customsoftware.medicalservices.domain.Organization;
import com.customsoftware.medicalservices.service.dto.OrganizationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrganizationMapper extends EntityMapper<OrganizationDTO, Organization> {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Organization toEntity(OrganizationDTO dto);
}
