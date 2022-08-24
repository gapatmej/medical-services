--
-- PostgreSQL database dump
--

-- Dumped from database version 12.12 (Ubuntu 12.12-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.12 (Ubuntu 12.12-0ubuntu0.20.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangelog (
                                          id character varying(255) NOT NULL,
                                          author character varying(255) NOT NULL,
                                          filename character varying(255) NOT NULL,
                                          dateexecuted timestamp without time zone NOT NULL,
                                          orderexecuted integer NOT NULL,
                                          exectype character varying(10) NOT NULL,
                                          md5sum character varying(35),
                                          description character varying(255),
                                          comments character varying(255),
                                          tag character varying(255),
                                          liquibase character varying(20),
                                          contexts character varying(255),
                                          labels character varying(255),
                                          deployment_id character varying(10)
);


ALTER TABLE public.databasechangelog OWNER TO postgres;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.databasechangeloglock (
                                              id integer NOT NULL,
                                              locked boolean NOT NULL,
                                              lockgranted timestamp without time zone,
                                              lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO postgres;

--
-- Name: jhi_authority; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jhi_authority (
    name character varying(50) NOT NULL
);


ALTER TABLE public.jhi_authority OWNER TO postgres;

--
-- Name: jhi_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jhi_user (
                                 id bigint NOT NULL,
                                 login character varying(50) NOT NULL,
                                 password_hash character varying(60) NOT NULL,
                                 first_name character varying(50),
                                 last_name character varying(50),
                                 email character varying(191),
                                 image_url character varying(256),
                                 activated boolean NOT NULL,
                                 lang_key character varying(10),
                                 activation_key character varying(20),
                                 reset_key character varying(20),
                                 created_by character varying(50) NOT NULL,
                                 created_date timestamp without time zone,
                                 reset_date timestamp without time zone,
                                 last_modified_by character varying(50),
                                 last_modified_date timestamp without time zone
);


ALTER TABLE public.jhi_user OWNER TO postgres;

--
-- Name: jhi_user_authority; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.jhi_user_authority (
                                           user_id bigint NOT NULL,
                                           authority_name character varying(50) NOT NULL
);


ALTER TABLE public.jhi_user_authority OWNER TO postgres;

--
-- Name: medical_certificade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.medical_certificade (
                                            id bigint NOT NULL,
                                            emission_date timestamp without time zone,
                                            first_name character varying(255),
                                            last_name character varying(255),
                                            address character varying(255),
                                            clinic_history_number character varying(255),
                                            identification_type character varying(255),
                                            identification character varying(255),
                                            phone character varying(255),
                                            mobile_phone character varying(255),
                                            attention_date timestamp without time zone,
                                            diagnosis character varying(255),
                                            rest_type character varying(255),
                                            from_date timestamp without time zone,
                                            until_date timestamp without time zone,
                                            total integer,
                                            observation character varying(255),
                                            symptom character varying(255)
);


ALTER TABLE public.medical_certificade OWNER TO postgres;

--
-- Name: sequence_generator; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sequence_generator
    START WITH 1050
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sequence_generator OWNER TO postgres;

--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.databasechangelog VALUES ('00000000000000', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2022-08-23 15:23:42.541392', 1, 'EXECUTED', '8:b8c27d9dc8db18b5de87cdb8c38a416b', 'createSequence sequenceName=sequence_generator', '', NULL, '4.3.1', NULL, NULL, '1286222311');
INSERT INTO public.databasechangelog VALUES ('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2022-08-23 15:23:42.583855', 2, 'EXECUTED', '8:ccc583fb9d33aab618c0bd69a0b4eb9d', 'createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...', '', NULL, '4.3.1', NULL, NULL, '1286222311');
INSERT INTO public.databasechangelog VALUES ('20220814193105-1', 'jhipster', 'config/liquibase/changelog/20220814193105_added_entity_MedicalCertificade.xml', '2022-08-23 15:23:42.599938', 3, 'EXECUTED', '8:2f63fc43117f43c2df0e96254d6d6311', 'createTable tableName=medical_certificade; dropDefaultValue columnName=emission_date, tableName=medical_certificade; dropDefaultValue columnName=attention_date, tableName=medical_certificade; dropDefaultValue columnName=from_date, tableName=medica...', '', NULL, '4.3.1', NULL, NULL, '1286222311');


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.databasechangeloglock VALUES (1, false, NULL, NULL);


--
-- Data for Name: jhi_authority; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.jhi_authority VALUES ('ROLE_ADMIN');
INSERT INTO public.jhi_authority VALUES ('ROLE_USER');


--
-- Data for Name: jhi_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.jhi_user VALUES (1, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', '', true, 'es', NULL, NULL, 'system', NULL, NULL, 'system', NULL);
INSERT INTO public.jhi_user VALUES (2, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', '', true, 'es', NULL, NULL, 'system', NULL, NULL, 'system', NULL);


--
-- Data for Name: jhi_user_authority; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.jhi_user_authority VALUES (1, 'ROLE_ADMIN');
INSERT INTO public.jhi_user_authority VALUES (1, 'ROLE_USER');
INSERT INTO public.jhi_user_authority VALUES (2, 'ROLE_USER');


--
-- Data for Name: medical_certificade; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: sequence_generator; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sequence_generator', 1050, false);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: jhi_authority jhi_authority_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jhi_authority
    ADD CONSTRAINT jhi_authority_pkey PRIMARY KEY (name);


--
-- Name: jhi_user_authority jhi_user_authority_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT jhi_user_authority_pkey PRIMARY KEY (user_id, authority_name);


--
-- Name: jhi_user jhi_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT jhi_user_pkey PRIMARY KEY (id);


--
-- Name: medical_certificade medical_certificade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.medical_certificade
    ADD CONSTRAINT medical_certificade_pkey PRIMARY KEY (id);


--
-- Name: jhi_user ux_user_email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT ux_user_email UNIQUE (email);


--
-- Name: jhi_user ux_user_login; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jhi_user
    ADD CONSTRAINT ux_user_login UNIQUE (login);


--
-- Name: jhi_user_authority fk_authority_name; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES public.jhi_authority(name);


--
-- Name: jhi_user_authority fk_user_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.jhi_user_authority
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.jhi_user(id);


--
-- PostgreSQL database dump complete
--
