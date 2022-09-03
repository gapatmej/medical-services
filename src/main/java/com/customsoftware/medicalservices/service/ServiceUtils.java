package com.customsoftware.medicalservices.service;

import com.customsoftware.medicalservices.config.ApplicationProperties;
import com.customsoftware.medicalservices.domain.MedicalCertificate;
import java.io.File;
import org.springframework.stereotype.Component;

@Component
public final class ServiceUtils {

    private static final String PATH_MEDICAL_CERTIFICATE = "medical-certificates";
    private static ApplicationProperties applicationProperties;

    public static String getMedicalCertificatePath(MedicalCertificate medicalCertificate) {
        return (
            applicationProperties.getPaths().getDocs() +
            PATH_MEDICAL_CERTIFICATE +
            File.separator +
            medicalCertificate.getDoctor().getDni() +
            File.separator +
            getMedicalCertificateName(medicalCertificate)
        );
    }

    public static String getMedicalCertificateName(MedicalCertificate medicalCertificate) {
        return medicalCertificate.getId() + "_" + medicalCertificate.getPatient().getDni() + ".pdf";
    }

    public static ApplicationProperties getApplicationProperties() {
        return ServiceUtils.applicationProperties;
    }

    public static void setApplicationProperties(ApplicationProperties applicationProperties) {
        ServiceUtils.applicationProperties = applicationProperties;
    }
}
