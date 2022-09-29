package com.customsoftware.medicalservices.repository.custom;

import com.customsoftware.medicalservices.domain.InternationalDisease;
import com.customsoftware.medicalservices.service.dto.search.SearchInternationalDiseaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomInternationalDiseaseRepository {
    Page<InternationalDisease> search(SearchInternationalDiseaseDTO internationalDiseaseDTO, Pageable pageable);
}
