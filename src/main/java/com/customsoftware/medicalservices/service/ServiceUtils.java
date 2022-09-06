package com.customsoftware.medicalservices.service;

import com.customsoftware.medicalservices.config.ApplicationProperties;
import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.domain.User;
import java.io.File;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public final class ServiceUtils {

    private static final String PATH_MEDICAL_CERTIFICATE = "medical-certificates";
    private static final String PATH_CERTIFICATES = "certificates";
    private static ApplicationProperties applicationProperties;

    public static String getCertificatePath(User user, String name) {
        return (applicationProperties.getPaths().getDocs() + PATH_CERTIFICATES + File.separator + user.getDni() + File.separator + name);
    }

    public static String getCertificatePath(User user) {
        return getCertificatePath(user, user.getCertificate());
    }

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
