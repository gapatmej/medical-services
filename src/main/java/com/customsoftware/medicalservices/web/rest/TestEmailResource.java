package com.customsoftware.medicalservices.web.rest;

import com.customsoftware.medicalservices.domain.Organization;
import com.customsoftware.medicalservices.domain.enumeration.MedicalCertificateStatus;
import com.customsoftware.medicalservices.repository.MedicalCertificateRepository;
import com.customsoftware.medicalservices.security.AccessConstants;
import com.customsoftware.medicalservices.service.MailService;
import com.customsoftware.medicalservices.service.OrganizationService;
import com.customsoftware.medicalservices.service.ServiceUtils;
import com.customsoftware.medicalservices.service.mapper.ReportService;
import com.customsoftware.medicalservices.web.rest.errors.MedicalServicesRuntimeException;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-email")
public class TestEmailResource {

    private final MailService mailService;
    private final ReportService reportService;
    private final MedicalCertificateRepository medicalCertificateRepository;
    private final OrganizationService organizationService;

    public TestEmailResource(
        MailService mailService,
        ReportService reportService,
        MedicalCertificateRepository medicalCertificateRepository,
        OrganizationService organizationService
    ) {
        this.mailService = mailService;
        this.reportService = reportService;
        this.medicalCertificateRepository = medicalCertificateRepository;
        this.organizationService = organizationService;
    }

    @PostMapping("/send-email")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize(AccessConstants.ADMIN)
    public void sendEmail(@RequestBody String to) {
        this.mailService.sendEmail(to, "Testing email", "This is a test.", false, false);
    }

    @PostMapping("/generate-certificate-report/{id}/{login}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize(AccessConstants.ADMIN)
    public void generateCertificateReport(@PathVariable Long id, @PathVariable String login) {
        medicalCertificateRepository
            .searchByIdAndDoctor(id, login)
            .ifPresent(
                mC -> {
                    Organization organization = organizationService.getOrganization();
                    try {
                        reportService.generateMedicalCertificate(organization, mC);
                    } catch (IOException e) {
                        throw new MedicalServicesRuntimeException(e.getMessage());
                    }
                }
            );
    }
}
