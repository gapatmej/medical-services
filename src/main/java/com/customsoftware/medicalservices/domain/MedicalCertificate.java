package com.customsoftware.medicalservices.domain;

import com.customsoftware.medicalservices.domain.enumeration.IdentificationType;
import java.io.Serializable;
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
public class MedicalCertificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "emission_date")
    private Instant emissionDate;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "clinic_history_number")
    private String clinicHistoryNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "identification_type")
    private IdentificationType identificationType;

    @Column(name = "identification")
    private String identification;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "attention_date")
    private Instant attentionDate;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "rest_type")
    private String restType;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "until_date")
    private Instant untilDate;

    @Column(name = "total")
    private Integer total;

    @Column(name = "observation")
    private String observation;

    @Column(name = "symptom")
    private String symptom;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedicalCertificate id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getEmissionDate() {
        return this.emissionDate;
    }

    public MedicalCertificate emissionDate(Instant emissionDate) {
        this.emissionDate = emissionDate;
        return this;
    }

    public void setEmissionDate(Instant emissionDate) {
        this.emissionDate = emissionDate;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public MedicalCertificate firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public MedicalCertificate lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public MedicalCertificate address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClinicHistoryNumber() {
        return this.clinicHistoryNumber;
    }

    public MedicalCertificate clinicHistoryNumber(String clinicHistoryNumber) {
        this.clinicHistoryNumber = clinicHistoryNumber;
        return this;
    }

    public void setClinicHistoryNumber(String clinicHistoryNumber) {
        this.clinicHistoryNumber = clinicHistoryNumber;
    }

    public IdentificationType getIdentificationType() {
        return this.identificationType;
    }

    public MedicalCertificate identificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
        return this;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentification() {
        return this.identification;
    }

    public MedicalCertificate identification(String identification) {
        this.identification = identification;
        return this;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getPhone() {
        return this.phone;
    }

    public MedicalCertificate phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return this.mobilePhone;
    }

    public MedicalCertificate mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Instant getAttentionDate() {
        return this.attentionDate;
    }

    public MedicalCertificate attentionDate(Instant attentionDate) {
        this.attentionDate = attentionDate;
        return this;
    }

    public void setAttentionDate(Instant attentionDate) {
        this.attentionDate = attentionDate;
    }

    public String getDiagnosis() {
        return this.diagnosis;
    }

    public MedicalCertificate diagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
        return this;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getRestType() {
        return this.restType;
    }

    public MedicalCertificate restType(String restType) {
        this.restType = restType;
        return this;
    }

    public void setRestType(String restType) {
        this.restType = restType;
    }

    public Instant getFromDate() {
        return this.fromDate;
    }

    public MedicalCertificate fromDate(Instant fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getUntilDate() {
        return this.untilDate;
    }

    public MedicalCertificate untilDate(Instant untilDate) {
        this.untilDate = untilDate;
        return this;
    }

    public void setUntilDate(Instant untilDate) {
        this.untilDate = untilDate;
    }

    public Integer getTotal() {
        return this.total;
    }

    public MedicalCertificate total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getObservation() {
        return this.observation;
    }

    public MedicalCertificate observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getSymptom() {
        return this.symptom;
    }

    public MedicalCertificate symptom(String symptom) {
        this.symptom = symptom;
        return this;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicalCertificate)) {
            return false;
        }
        return id != null && id.equals(((MedicalCertificate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MedicalCertificate{" +
            "id=" + getId() +
            ", emissionDate='" + getEmissionDate() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", address='" + getAddress() + "'" +
            ", clinicHistoryNumber='" + getClinicHistoryNumber() + "'" +
            ", identificationType='" + getIdentificationType() + "'" +
            ", identification='" + getIdentification() + "'" +
            ", phone='" + getPhone() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", attentionDate='" + getAttentionDate() + "'" +
            ", diagnosis='" + getDiagnosis() + "'" +
            ", restType='" + getRestType() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", untilDate='" + getUntilDate() + "'" +
            ", total=" + getTotal() +
            ", observation='" + getObservation() + "'" +
            ", symptom='" + getSymptom() + "'" +
            "}";
    }
}
