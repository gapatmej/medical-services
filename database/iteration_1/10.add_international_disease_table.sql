CREATE TABLE international_disease
(
    id                 bigserial                 NOT NULL,
    cod                character varying(4)   NOT NULL,
    description        character varying(150) NOT NULL,
    created_date       timestamp              not null default CURRENT_TIMESTAMP,
    created_by         varchar(50)            NOT NULL,
    last_modified_date timestamp NULL DEFAULT NULL,
    last_modified_by   varchar(50)                     DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT international_disease_uniques UNIQUE (cod)
);
