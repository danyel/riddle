--liquibase formatted sql
--changeset dnoulet:V1_000006__create_participants
CREATE TABLE participants
(
    ID           UUID PRIMARY KEY,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    stored_token VARCHAR(255) NULL,
    created_by   VARCHAR(50)  NOT NULL,
    updated_by   VARCHAR(50),
    CREATED_AT   TIMESTAMP default now(),
    UPDATED_AT   TIMESTAMP
);
