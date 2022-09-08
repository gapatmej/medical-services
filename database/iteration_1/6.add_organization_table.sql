CREATE TABLE organization
(
    id                 bigint                 NOT NULL,
    name               character varying(150) NOT NULL,
    email              character varying(100) NOT NULL,
    phone_number       character varying(20)  NOT NULL,
    address            character varying(255) NOT NULL,
    created_date       timestamp              not null default CURRENT_TIMESTAMP,
    created_by         varchar(50)            NOT NULL,
    last_modified_date timestamp NULL DEFAULT NULL,
    last_modified_by   varchar(50)                     DEFAULT NULL,
    PRIMARY KEY (id)
);
