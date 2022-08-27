package com.customsoftware.medicalservices.repository.custom;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.service.dto.search.SearchMedicalCertificateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomMedicalCertificateRepository {
    Page<MedicalCertificate> search(SearchMedicalCertificateDTO searchMedicalCertificateDTO, Pageable pageable);
}
