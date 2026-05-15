--liquibase formatted sql
--changeset dnoulet:V1_000006__create_participants
CREATE TABLE participants
(
    ID           UUID PRIMARY KEY,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    PHOTO bytea,
    cv    bytea,
    stored_token VARCHAR(255) NULL,
    created_by   VARCHAR(50)  NOT NULL,
    updated_by   VARCHAR(50),
    CREATED_AT   TIMESTAMP default now(),
    UPDATED_AT   TIMESTAMP
);

--changeset dnoulet:V1_000006__create_participants_data context:lcl
INSERT INTO participants(id, first_name, last_name, email, created_by, CREATED_AT, UPDATED_AT)
values ('dbc132cc-b918-40fa-b2e9-81032635c4e8', 'Daniel', 'Noulet', 'daniel.noulet@email.net', 'admin', now(), now());