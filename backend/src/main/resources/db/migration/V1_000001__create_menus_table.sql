--liquibase formatted sql
--changeset dnoulet:V1_000001_create_menus
CREATE TABLE menus
(
    ID          UUID PRIMARY KEY,
    PATH        VARCHAR(255) NOT NULL,
    LABEL       VARCHAR(100) NOT NULL,
    ICON       VARCHAR(100) NOT NULL,
    ORDER_INDEX INT       DEFAULT 0,
    CREATED_AT  TIMESTAMP default now(),
    UPDATED_AT  TIMESTAMP
);

--changeset dnoulet:V1_000001_create_menus_data context:lcl
INSERT INTO menus (id, label, icon, path, order_index)
VALUES (gen_random_uuid(), 'Questions', 'vaadin:dashboard', '/questions', 0);
INSERT INTO menus (id, label, icon, path, order_index)
VALUES (gen_random_uuid(), 'Icons', 'vaadin:eye', '/icons', 1);
