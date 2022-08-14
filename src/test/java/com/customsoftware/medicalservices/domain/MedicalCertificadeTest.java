package com.customsoftware.medicalservices.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.customsoftware.medicalservices.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicalCertificadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalCertificade.class);
        MedicalCertificade medicalCertificade1 = new MedicalCertificade();
        medicalCertificade1.setId(1L);
        MedicalCertificade medicalCertificade2 = new MedicalCertificade();
        medicalCertificade2.setId(medicalCertificade1.getId());
        assertThat(medicalCertificade1).isEqualTo(medicalCertificade2);
        medicalCertificade2.setId(2L);
        assertThat(medicalCertificade1).isNotEqualTo(medicalCertificade2);
        medicalCertificade1.setId(null);
        assertThat(medicalCertificade1).isNotEqualTo(medicalCertificade2);
    }
}
