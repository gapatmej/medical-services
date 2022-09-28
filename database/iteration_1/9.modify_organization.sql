ALTER TABLE organization ADD COLUMN city character varying(50) NOT NULL CHECK (city <> '') default 'Quito';
ALTER TABLE organization ALTER COLUMN city DROP DEFAULT;
