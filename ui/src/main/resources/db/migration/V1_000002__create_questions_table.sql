--liquibase formatted sql
--changeset dnoulet:V1_000001_create_questions
CREATE TABLE questions
(
    ID         UUID PRIMARY KEY,
    QUESTION   text         NOT NULL,
    TYPE       VARCHAR(100) NOT NULL,
    CREATED_AT TIMESTAMP default now(),
    UPDATED_AT TIMESTAMP
);

--changeset dnoulet:V1_000001_create_questions_data context:lcl
INSERT INTO questions (id, question, type)
VALUES ('6d2c7dee-740a-47f1-99b0-4e4167182ae7', 'Question 1', 'SINGLE_CHOICE');
INSERT INTO questions (id, question, type)
VALUES ('3cee42fb-d8da-4127-8f0e-3ddfab263f34', 'Question 2', 'OPEN');
INSERT INTO questions (id, question, type)
VALUES ('a2b20b03-9d96-41d4-8e83-a35b21c21fe7', 'Question 3', 'MULTIPLE_CHOICE');
