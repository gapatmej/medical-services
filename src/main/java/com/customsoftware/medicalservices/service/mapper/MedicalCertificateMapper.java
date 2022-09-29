package com.customsoftware.medicalservices.service.mapper;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.service.dto.MedicalCertificateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, InternationalDiseaseMapper.class })
public interface MedicalCertificateMapper extends EntityMapper<MedicalCertificateDTO, MedicalCertificate> {
    @Mapping(target = "doctor", source = "doctor", qualifiedByName = "id")
    @Mapping(target = "patient", source = "patient", qualifiedByName = "id")
    @Mapping(target = "internationalDisease", source = "internationalDisease", qualifiedByName = "id")
    MedicalCertificateDTO toDto(MedicalCertificate medicalCertificate);

    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    MedicalCertificate toEntity(MedicalCertificateDTO medicalCertificateDTO);
}
