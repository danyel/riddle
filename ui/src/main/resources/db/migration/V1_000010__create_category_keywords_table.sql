--liquibase formatted sql
--changeset dnoulet:V1_000010__create_category_keywords_table
CREATE TABLE category_keywords
(
    id          UUID PRIMARY KEY,
    category_id UUID         NOT NULL,
    word        VARCHAR(255) NOT NULL,
    CONSTRAINT fk_keywords_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE
);

CREATE INDEX idx_keywords_category_id ON category_keywords (category_id);
CREATE INDEX idx_keywords_word_lower ON category_keywords (LOWER(word));