package com.customsoftware.medicalservices.service.mapper;

import com.customsoftware.medicalservices.domain.InternationalDisease;
import com.customsoftware.medicalservices.service.dto.InternationalDiseaseDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface InternationalDiseaseMapper extends EntityMapper<InternationalDiseaseDTO, InternationalDisease> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InternationalDiseaseDTO toDtoId(InternationalDisease entity);
}
