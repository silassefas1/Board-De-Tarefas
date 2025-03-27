--liquibase formatted sql
--changeset silas:202503271605
--comment: create cancel_reason column

ALTER TABLE CARDS ADD COLUMN cancel_reason VARCHAR(255);

--rollback ALTER TABLE CARDS DROP COLUMN cancel_reason;


