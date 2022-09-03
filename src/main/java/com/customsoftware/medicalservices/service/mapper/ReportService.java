package com.customsoftware.medicalservices.service.mapper;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import java.io.IOException;

public interface ReportService {
    void generateMedicalCertificate(MedicalCertificate medicalCertificate) throws IOException;
}
