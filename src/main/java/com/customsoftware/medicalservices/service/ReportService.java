package com.customsoftware.medicalservices.service;

import com.customsoftware.medicalservices.config.ApplicationProperties;
import com.customsoftware.medicalservices.domain.MedicalCertificate;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
public class ReportService {

    private static final String MEDICAL_CERTIFICATE = "medicalCertificate";

    private final Logger log = LoggerFactory.getLogger(ReportService.class);
    private final SpringTemplateEngine templateEngine;

    public ReportService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void generateMedicalCertificate(MedicalCertificate medicalCertificate) {
        try {
            Locale locale = Locale.forLanguageTag(medicalCertificate.getDoctor().getLangKey());
            Context context = new Context(locale);
            context.setVariable(MEDICAL_CERTIFICATE, medicalCertificate);
            String content = templateEngine.process("reports/medicalCertificate", context);

            generatePdfFromHtml(medicalCertificate, content);
        } catch (IOException e) {
            log.error("Error trying to generate medical certificate: {} ", e.getMessage());
        }
    }

    private void generatePdfFromHtml(MedicalCertificate medicalCertificate, String html) throws IOException {
        File yourFile = new File(ServiceUtils.getMedicalCertificatePath(medicalCertificate));
        yourFile.getParentFile().mkdirs();
        yourFile.createNewFile();

        OutputStream outputStream = new FileOutputStream(yourFile);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }
}
