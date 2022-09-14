package com.customsoftware.medicalservices.service.mapper;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.domain.Organization;
import java.io.IOException;

public interface ReportService {
    void generateMedicalCertificate(Organization organization, MedicalCertificate medicalCertificate) throws IOException;
}
