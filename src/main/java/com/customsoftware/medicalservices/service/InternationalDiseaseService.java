package com.customsoftware.medicalservices.service;

import com.customsoftware.medicalservices.service.dto.InternationalDiseaseDTO;
import com.customsoftware.medicalservices.service.dto.search.SearchInternationalDiseaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InternationalDiseaseService {
    Page<InternationalDiseaseDTO> search(Pageable pageable, SearchInternationalDiseaseDTO searchInternationalDiseaseDTO);
}
