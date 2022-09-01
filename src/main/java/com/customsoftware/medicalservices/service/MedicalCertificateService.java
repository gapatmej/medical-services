package com.customsoftware.medicalservices.service;

import com.customsoftware.medicalservices.service.dto.MedicalCertificateDTO;
import com.customsoftware.medicalservices.service.dto.search.SearchMedicalCertificateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicalCertificateService {
    MedicalCertificateDTO save(MedicalCertificateDTO medicalCertificateDTO);

    Page<MedicalCertificateDTO> search(Pageable pageable, SearchMedicalCertificateDTO searchMedicalCertificateDTO);

    Optional<MedicalCertificateDTO> searchById(Long id);

    void delete(Long id);
    void sign(Long id);
}
