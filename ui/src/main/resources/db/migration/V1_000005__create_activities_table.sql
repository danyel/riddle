--liquibase formatted sql
--changeset dnoulet:V1_000005__create_activities
CREATE TABLE activities
(
    ID              UUID PRIMARY KEY,
    question_id     UUID         NOT NULL,
    element_id      VARCHAR(255) NOT NULL,
    action_type     VARCHAR(255) NOT NULL,
    username        VARCHAR(255) NOT NULL,
    additional_data TEXT         NOT NULL,
    action_time     TIMESTAMP,
    CREATED_AT      TIMESTAMP default now(),
    UPDATED_AT      TIMESTAMP
);
