package com.customsoftware.medicalservices.domain;

import com.customsoftware.medicalservices.domain.enumeration.IdentificationType;
import com.customsoftware.medicalservices.domain.enumeration.MedicalCertificateStatus;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MedicalCertificate.
 */
@Entity
@Table(name = "medical_certificate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MedicalCertificate extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @Column(name = "emission_date", nullable = false)
    private Instant emissionDate;

    @Column(name = "emission_place", nullable = false, length = 50)
    private String emissionPlace;

    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    @Column(name = "cie10_cod", nullable = false, length = 10)
    private String cie10Cod;

    @Column(nullable = false)
    private boolean symptoms;

    @Column(nullable = false)
    private boolean disease;

    @Column(name = "disease_description")
    private String diseaseDescription;

    @Column(nullable = false)
    private boolean insulation;

    @Column(name = "insulation_description")
    private String insulationDescription;

    @Column(name = "total_days_off", nullable = false)
    private Integer totalDaysOff;

    @Column(name = "from_date", nullable = false)
    private Instant fromDate;

    @Column(name = "until_date", nullable = false)
    private Instant untilDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private MedicalCertificateStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
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
