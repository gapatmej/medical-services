package com.customsoftware.medicalservices.service.dto.search;

import java.io.Serializable;

public class SearchMedicalCertificateDTO implements Serializable {

    private String query;

    private String doctorLogin;
    private String patientLogin;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getDoctorLogin() {
        return doctorLogin;
    }

    public void setDoctorLogin(String doctorLogin) {
        this.doctorLogin = doctorLogin;
    }

    public String getPatientLogin() {
        return patientLogin;
    }

    public void setPatientLogin(String patientLogin) {
        this.patientLogin = patientLogin;
    }
}
