alter table medical_certificate drop column cie10_cod;

ALTER TABLE medical_certificate
    ADD COLUMN international_disease_id bigint NOT NULL default 1,
    ADD CONSTRAINT fk_medical_certificate__international_disease_id FOREIGN KEY (international_disease_id) REFERENCES international_disease (id);

ALTER TABLE medical_certificate ALTER COLUMN international_disease_id DROP DEFAULT;
