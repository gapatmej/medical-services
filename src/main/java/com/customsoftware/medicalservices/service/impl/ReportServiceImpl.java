package com.customsoftware.medicalservices.service.impl;

import com.customsoftware.medicalservices.domain.MedicalCertificate;
import com.customsoftware.medicalservices.domain.Organization;
import com.customsoftware.medicalservices.service.ServiceUtils;
import com.customsoftware.medicalservices.service.mapper.ReportService;
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
public class ReportServiceImpl extends AbstractServiceImpl implements ReportService {

    private static final String MEDICAL_CERTIFICATE = "medicalCertificate";
    private static final String ORGANIZATION = "organization";
    private final SpringTemplateEngine templateEngine;

    public ReportServiceImpl(SpringTemplateEngine templateEngine) {
        super(ReportServiceImpl.class);
        this.templateEngine = templateEngine;
    }

    public void generateMedicalCertificate(Organization organization, MedicalCertificate medicalCertificate) throws IOException {
        Locale locale = Locale.forLanguageTag(medicalCertificate.getDoctor().getLangKey());
        Context context = new Context(locale);
        context.setVariable(ORGANIZATION, organization);
        context.setVariable(MEDICAL_CERTIFICATE, medicalCertificate);
        String content = templateEngine.process("reports/medicalCertificate", context);

        generatePdfFromHtml(medicalCertificate, content);
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
