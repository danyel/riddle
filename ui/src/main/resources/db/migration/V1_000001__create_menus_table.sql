--liquibase formatted sql
--changeset dnoulet:V1_000001_create_menus
CREATE TABLE menus
(
    ID          UUID PRIMARY KEY,
    PATH        VARCHAR(255) NOT NULL,
    LABEL       VARCHAR(100) NOT NULL,
    ICON        VARCHAR(100) NOT NULL,
    ORDER_INDEX INT       DEFAULT 0,
    CREATED_AT  TIMESTAMP default now(),
    UPDATED_AT  TIMESTAMP
);

CREATE TABLE menu_configurations
(
    ID         UUID PRIMARY KEY,
    MENU_ID    UUID         NOT NULL,
    USERNAME   VARCHAR(100) NOT NULL,
    CREATED_AT TIMESTAMP default now(),
    UPDATED_AT TIMESTAMP
);

--changeset dnoulet:V1_000001_create_menus_data context:lcl
INSERT INTO menus (id, label, icon, path, order_index)
VALUES ('bfc25c06-5a0a-42e3-9828-ae2ec16570b9', 'Questions', 'vaadin:dashboard', '/#/questions', 0);
INSERT INTO menus (id, label, icon, path, order_index)
VALUES ('095a7040-3fec-4675-924d-2515f777305c', 'Participants', 'vaadin:user-clock', '/#/participants', 1);
INSERT INTO menu_configurations(id, menu_id, username)
VALUES ('4709ebc1-139a-4e9f-be70-8003f13a634f', 'bfc25c06-5a0a-42e3-9828-ae2ec16570b9', 'dnoulet');
INSERT INTO menu_configurations(id, menu_id, username)
VALUES ('3a7a8cb6-7e68-448d-9a0f-8b833f39290f', '095a7040-3fec-4675-924d-2515f777305c', 'dnoulet');