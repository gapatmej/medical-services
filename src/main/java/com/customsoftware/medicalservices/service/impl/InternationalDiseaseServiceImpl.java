package com.customsoftware.medicalservices.service.impl;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.repository.InternationalDiseaseRepository;
import com.customsoftware.medicalservices.service.*;
import com.customsoftware.medicalservices.service.dto.InternationalDiseaseDTO;
import com.customsoftware.medicalservices.service.dto.MedicalCertificateDTO;
import com.customsoftware.medicalservices.service.dto.search.SearchInternationalDiseaseDTO;
import com.customsoftware.medicalservices.service.mapper.InternationalDiseaseMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InternationalDiseaseServiceImpl extends AbstractServiceImpl implements InternationalDiseaseService {

    private final InternationalDiseaseMapper internationalDiseaseMapper;

    private final InternationalDiseaseRepository internationalDiseaseRepository;

    public InternationalDiseaseServiceImpl(
        InternationalDiseaseMapper internationalDiseaseMapper,
        InternationalDiseaseRepository internationalDiseaseRepository
    ) {
        super(InternationalDiseaseServiceImpl.class);
        this.internationalDiseaseMapper = internationalDiseaseMapper;
        this.internationalDiseaseRepository = internationalDiseaseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InternationalDiseaseDTO> search(Pageable pageable, SearchInternationalDiseaseDTO searchInternationalDiseaseDTO) {
        log.debug("Request to get all InternationalDisease");
        return internationalDiseaseRepository.search(searchInternationalDiseaseDTO, pageable).map(internationalDiseaseMapper::toDto);
    }
}
