--liquibase formatted sql
--changeset dnoulet:V1_000007__create_publications_and_levels_and_positions_table

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


--changeset dnoulet:V1_000007__create_publications_and_levels_and_positions_table_data context:lcl

INSERT INTO levels (id, created_by, level)
VALUES ('2efffe3c-e082-4dec-91d4-61ae0b7b0209', 'admin', 'MEDIOR');

INSERT INTO positions (id, created_by, position)
VALUES ('56091d8b-a9b8-47fb-b233-1448d38d3772', 'admin', 'SOL_DES');

INSERT INTO publications (id, created_by, title, description, proposal, position_id,
                          level_id)
VALUES ('b6882b57-f745-45f2-adbe-4e22dce0ae52', 'admin', 'Solution Designer', 'In need for a good developer',
        'A lot of money',
        '56091d8b-a9b8-47fb-b233-1448d38d3772', '2efffe3c-e082-4dec-91d4-61ae0b7b0209');
