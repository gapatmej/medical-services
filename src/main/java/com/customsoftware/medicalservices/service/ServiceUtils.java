package com.customsoftware.medicalservices.service;

import com.customsoftware.medicalservices.config.ApplicationProperties;
import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.web.rest.errors.MedicalServicesRuntimeException;
import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public final class ServiceUtils {

    private static final String PATH_MEDICAL_CERTIFICATE = "medical-certificates";
    private static ApplicationProperties applicationProperties;

    public static void setApplicationProperties(ApplicationProperties applicationProperties) {
        ServiceUtils.applicationProperties = applicationProperties;
    }

    public static String getMedicalCertificatePath(MedicalCertificate medicalCertificate) {
        return (
            applicationProperties.getPaths().getDocs() +
            PATH_MEDICAL_CERTIFICATE +
            File.separator +
            medicalCertificate.getDoctor().getDni() +
            File.separator +
            medicalCertificate.getId() +
            ".pdf"
        );
    }

    public static ApplicationProperties getApplicationProperties() {
        return ServiceUtils.applicationProperties;
    }
}
