--liquibase formatted sql

--changeset grayroom:1
CREATE TABLE IF NOT EXISTS company
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);
--rollback drop table company

--changeset grayroom:2
CREATE TABLE IF NOT EXISTS company_locales
(
    company_id  INT REFERENCES company (id) on delete cascade,
    lang        VARCHAR(2),
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (company_id, lang)
);

--changeset grayroom:3
CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(64) NOT NULL UNIQUE,
    birth_date DATE,
    firstname  VARCHAR(64),
    lastname   VARCHAR(64),
    role       VARCHAR(32),
    company_id INT REFERENCES company (id)
);

--changeset grayroom:4
CREATE TABLE IF NOT EXISTS payment
(
    id          BIGSERIAL PRIMARY KEY,
    amount      INT    NOT NULL,
    receiver_id BIGINT NOT NULL REFERENCES users (id) on delete cascade
);

--changeset grayroom:5
CREATE TABLE IF NOT EXISTS chat
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

--changeset grayroom:6
CREATE TABLE IF NOT EXISTS users_chat
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users (id) on delete cascade,
    chat_id BIGINT NOT NULL REFERENCES chat (id) on delete cascade,
    UNIQUE (user_id, chat_id)
);
--changeset grayroom:7
create table if not exists revision
(
    id        serial not null primary key,
    timestamp bigint
);

--changeset grayroom:8
create table if not exists users_aud
(
    id         bigint  not null,
    rev        integer not null references revision (id),
    revtype    smallint,
    birth_date date,
    firstname  varchar(64),
    lastname   varchar(64),
    role       varchar(32),
    username   varchar(64),
    company_id integer,
    primary key (rev, id)
);