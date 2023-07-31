--liquibase formatted sql

--changeset grayroom:1
ALTER TABLE IF EXISTS users
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;

ALTER TABLE IF EXISTS users
    ADD COLUMN IF NOT EXISTS modified_at TIMESTAMP;

ALTER TABLE IF EXISTS users
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(32);

ALTER TABLE IF EXISTS users
    ADD COLUMN IF NOT EXISTS modified_by VARCHAR(32);