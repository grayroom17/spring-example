--liquibase formatted sql

--changeset grayroom:1
ALTER TABLE IF EXISTS users
    ADD COLUMN IF NOT EXISTS password varchar(128) default '{noop}password';

--changeset grayroom:2
ALTER TABLE IF EXISTS users_aud
    ADD COLUMN IF NOT EXISTS password varchar(128);