package com.customsoftware.medicalservices.service.dto;

import com.customsoftware.medicalservices.domain.enumeration.MedicalCertificateStatus;
import java.io.Serializable;
import java.time.Instant;

public class MedicalCertificateDTO implements Serializable {

    private Long id;

    private AdminUserDTO doctor;

    private AdminUserDTO patient;

    private Instant emissionDate;

    private String emissionPlace;

    private String diagnosis;

    private String cie10Cod;

    private boolean symptoms;

    private boolean disease;

    private String diseaseDescription;

    private boolean insulation;

    private String insulationDescription;

    private Integer totalDaysOff;

    private Instant fromDate;

    private Instant untilDate;

    private MedicalCertificateStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdminUserDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(AdminUserDTO doctor) {
        this.doctor = doctor;
    }

    public AdminUserDTO getPatient() {
        return patient;
    }

    public void setPatient(AdminUserDTO patient) {
        this.patient = patient;
    }

    public Instant getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(Instant emissionDate) {
        this.emissionDate = emissionDate;
    }

    public String getEmissionPlace() {
        return emissionPlace;
    }

    public void setEmissionPlace(String emissionPlace) {
        this.emissionPlace = emissionPlace;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getCie10Cod() {
        return cie10Cod;
    }

    public void setCie10Cod(String cie10Cod) {
        this.cie10Cod = cie10Cod;
    }

    public boolean isSymptoms() {
        return symptoms;
    }

    public void setSymptoms(boolean symptoms) {
        this.symptoms = symptoms;
    }

    public boolean isDisease() {
        return disease;
    }

    public void setDisease(boolean disease) {
        this.disease = disease;
    }

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    public boolean isInsulation() {
        return insulation;
    }

    public void setInsulation(boolean insulation) {
        this.insulation = insulation;
    }

    public String getInsulationDescription() {
        return insulationDescription;
    }

    public void setInsulationDescription(String insulationDescription) {
        this.insulationDescription = insulationDescription;
    }

    public Integer getTotalDaysOff() {
        return totalDaysOff;
    }

    public void setTotalDaysOff(Integer totalDaysOff) {
        this.totalDaysOff = totalDaysOff;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getUntilDate() {
        return untilDate;
    }

    public void setUntilDate(Instant untilDate) {
        this.untilDate = untilDate;
    }

    public MedicalCertificateStatus getStatus() {
        return status;
    }

    public void setStatus(MedicalCertificateStatus status) {
        this.status = status;
    }
}
