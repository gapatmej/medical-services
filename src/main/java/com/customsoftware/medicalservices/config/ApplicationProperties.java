package com.customsoftware.medicalservices.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Medical Services.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private Paths paths = new Paths();
    private Urls urls = new Urls();

    public Paths getPaths() {
        return paths;
    }

    public void setPaths(Paths paths) {
        this.paths = paths;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public class Paths {

        private String docs;

        public String getDocs() {
            return docs;
        }

        public void setDocs(String docs) {
            this.docs = docs;
        }
    }

    public class Urls {

        private String validateCertificateRevoked;
        private String dateToSign;

        public String getValidateCertificateRevoked() {
            return validateCertificateRevoked;
        }

        public void setValidateCertificateRevoked(String validateCertificateRevoked) {
            this.validateCertificateRevoked = validateCertificateRevoked;
        }

        public String getDateToSign() {
            return dateToSign;
        }

        public void setDateToSign(String dateToSign) {
            this.dateToSign = dateToSign;
        }
    }
}
