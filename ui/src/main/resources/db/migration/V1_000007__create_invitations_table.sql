--liquibase formatted sql
--changeset dnoulet:V1_000007__create_invitations
CREATE TABLE invitations
(
    ID             UUID PRIMARY KEY,
    PARTICIPANT_ID UUID        NOT NULL,
    created_by     VARCHAR(50) NOT NULL,
    updated_by     VARCHAR(50),
    CREATED_AT     TIMESTAMP default now(),
    UPDATED_AT     TIMESTAMP
);

CREATE TABLE invitation_questions
(
    invitation_id UUID NOT NULL,
    question_id   UUID NOT NULL,
    CONSTRAINT fk_invitation FOREIGN KEY (invitation_id)
        REFERENCES invitations (id) ON DELETE CASCADE,
    PRIMARY KEY (invitation_id, question_id)
);

CREATE INDEX idx_invitation_questions_invitation ON invitation_questions (invitation_id);