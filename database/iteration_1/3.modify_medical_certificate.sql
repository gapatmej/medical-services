ALTER TABLE medical_certificade RENAME TO medical_certificate;

alter table medical_certificate drop column attention_date;
alter table medical_certificate drop column first_name;
alter table medical_certificate drop column last_name;
alter table medical_certificate drop column address;
alter table medical_certificate drop column clinic_history_number;
alter table medical_certificate drop column identification_type;
alter table medical_certificate drop column identification;
alter table medical_certificate drop column phone;
alter table medical_certificate drop column mobile_phone;
alter table medical_certificate drop column total;
alter table medical_certificate drop column rest_type;
alter table medical_certificate drop column observation;
alter table medical_certificate drop column symptom;

ALTER TABLE medical_certificate
    ADD COLUMN doctor_id bigint NOT NULL,
    ADD CONSTRAINT fk_medical_certificate__doctor_id FOREIGN KEY (doctor_id) REFERENCES jhi_user (id);

ALTER TABLE medical_certificate
    ADD COLUMN patient_id bigint NOT NULL,
    ADD CONSTRAINT fk_medical_certificate__patient_id FOREIGN KEY (patient_id) REFERENCES jhi_user (id);

ALTER TABLE medical_certificate ADD COLUMN emission_place character varying(50) NOT NULL CHECK (emission_place <> '');
ALTER TABLE medical_certificate ADD COLUMN cie10_cod character varying(10) NOT NULL CHECK (cie10_cod <> '');
ALTER TABLE medical_certificate ADD COLUMN symptoms boolean NOT NULL;
ALTER TABLE medical_certificate ADD COLUMN disease boolean NOT NULL;
ALTER TABLE medical_certificate ADD COLUMN disease_description character varying(255) NOT NULL CHECK (disease_description <> '');
ALTER TABLE medical_certificate ADD COLUMN insulation boolean NOT NULL;
ALTER TABLE medical_certificate ADD COLUMN insulation_description character varying(255) NOT NULL CHECK (insulation_description <> '');
ALTER TABLE medical_certificate ADD COLUMN total_days_off integer NOT NULL;

/* Audit columns */
alter table medical_certificate add created_date timestamp not null default CURRENT_TIMESTAMP;
alter table medical_certificate add created_by varchar(50) NOT NULL;
alter table medical_certificate add last_modified_date timestamp NULL DEFAULT NULL;
alter table medical_certificate add last_modified_by varchar(50) DEFAULT NULL;
