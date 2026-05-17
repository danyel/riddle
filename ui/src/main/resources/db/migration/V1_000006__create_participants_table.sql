--liquibase formatted sql
--changeset dnoulet:V1_000006__create_participants
CREATE TABLE participants
(
    ID           UUID PRIMARY KEY,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    PHOTO      bytea,
    cv         bytea,
    stored_token VARCHAR(255) NULL,
    created_by   VARCHAR(50)  NOT NULL,
    updated_by   VARCHAR(50),
    CREATED_AT TIMESTAMP WITHOUT TIME ZONE default now(),
    UPDATED_AT TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE categories
(
    id         UUID PRIMARY KEY,
    created_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_by VARCHAR(255),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    name       VARCHAR(255) NOT NULL,
    CONSTRAINT uq_categories_name UNIQUE (name)
);

CREATE TABLE participant_categories
(
    participant_id UUID NOT NULL,
    category_id    UUID NOT NULL,
    PRIMARY KEY (participant_id, category_id),
    CONSTRAINT fk_part_cat_participant FOREIGN KEY (participant_id) REFERENCES participants (id) ON DELETE CASCADE,
    CONSTRAINT fk_part_cat_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

CREATE INDEX idx_part_cat_participant_id ON participant_categories (participant_id);
CREATE INDEX idx_part_cat_category_id ON participant_categories (category_id);


--changeset dnoulet:V1_000006__create_participants_data context:lcl
INSERT INTO participants(id, first_name, last_name, email, created_by, CREATED_AT, UPDATED_AT)
values ('dbc132cc-b918-40fa-b2e9-81032635c4e8', 'Daniel', 'Noulet', 'daniel.noulet@email.net', 'admin', now(), now());