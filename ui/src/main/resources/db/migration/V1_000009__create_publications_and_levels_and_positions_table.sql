--liquibase formatted sql
--changeset dnoulet:V1_000009__create_publications_and_levels_and_positions_table

CREATE TABLE positions
(
    id         UUID PRIMARY KEY,
    created_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_by VARCHAR(255),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    position   VARCHAR(255) NOT NULL
);

CREATE TABLE levels
(
    id         UUID PRIMARY KEY,
    created_by VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_by VARCHAR(255),
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    level      VARCHAR(255) NOT NULL
);

CREATE TABLE publications
(
    id          UUID PRIMARY KEY,
    created_by  VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_by  VARCHAR(255),
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    title       VARCHAR(255),
    description TEXT,
    proposal    TEXT,
    position_id UUID NOT NULL,
    level_id    UUID NOT NULL,

    CONSTRAINT fk_publications_position FOREIGN KEY (position_id) REFERENCES positions (id),
    CONSTRAINT fk_publications_level FOREIGN KEY (level_id) REFERENCES levels (id)
);

CREATE INDEX idx_publications_position_id ON publications (position_id);
CREATE INDEX idx_publications_level_id ON publications (level_id);
