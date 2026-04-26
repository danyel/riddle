--liquibase formatted sql
--changeset dnoulet:V1_000001_create_questions
CREATE TABLE questions
(
    ID          UUID PRIMARY KEY,
    QUESTION        VARCHAR(255) NOT NULL,
    TYPE       VARCHAR(100) NOT NULL,
    CREATED_AT  TIMESTAMP default now(),
    UPDATED_AT  TIMESTAMP
);

--changeset dnoulet:V1_000001_create_questions_data context:lcl
INSERT INTO questions (id, question, type)
VALUES (gen_random_uuid(), 'Question 1', 'SINGLE_CHOICE');
INSERT INTO questions (id, question, type)
VALUES (gen_random_uuid(), 'Question 2', 'OPEN');
INSERT INTO questions (id, question, type)
VALUES (gen_random_uuid(), 'Question 3', 'MULTIPLE_CHOICE');
