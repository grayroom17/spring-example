--liquibase formatted sql

--changeset grayroom:1
ALTER TABLE IF EXISTS users
    ADD COLUMN IF NOT EXISTS image varchar(64);

--changeset grayroom:2
ALTER TABLE IF EXISTS users_aud
    ADD COLUMN IF NOT EXISTS image varchar(64);