ALTER TABLE jhi_user ALTER COLUMN first_name SET NOT NULL;
ALTER TABLE jhi_user ALTER COLUMN last_name SET NOT NULL;
ALTER TABLE jhi_user ALTER COLUMN email SET NOT NULL;

ALTER TABLE jhi_user ADD COLUMN address character varying(100) NOT NULL CHECK (address <> '') DEFAULT 'user test';
ALTER TABLE jhi_user ALTER COLUMN address DROP DEFAULT;
ALTER TABLE jhi_user ADD COLUMN contact_phone_number character varying(10) NOT NULL CHECK (contact_phone_number <> '') DEFAULT 'user test';
ALTER TABLE jhi_user ALTER COLUMN contact_phone_number DROP DEFAULT;
ALTER TABLE jhi_user ADD COLUMN occupation character varying(100) NOT NULL CHECK (occupation <> '') DEFAULT 'user test';
ALTER TABLE jhi_user ALTER COLUMN occupation DROP DEFAULT;
ALTER TABLE jhi_user ADD COLUMN dni character varying(10) NOT NULL CHECK (dni <> '') DEFAULT 'test';
ALTER TABLE jhi_user ALTER COLUMN dni DROP DEFAULT;
UPDATE jhi_user set dni = id;
ALTER TABLE jhi_user ADD CONSTRAINT jhi_user_dni_unique UNIQUE (dni);
ALTER TABLE jhi_user ADD COLUMN identification_type character varying(10) NOT NULL CHECK (identification_type <> '') DEFAULT 'PASSPORT';
ALTER TABLE jhi_user ALTER COLUMN identification_type DROP DEFAULT;
ALTER TABLE jhi_user ADD COLUMN medical_history_number character varying(10);

