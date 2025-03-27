--liquibase formatted sql
--changeset silas:202503271350
--comment: set unblock_reason nullable

ALTER TABLE board.blocks MODIFY COLUMN unblocked_reason varchar(255) NULL;


--rollback ALTER TABLE BLOCKS MODIFY COLUMN unblock_reason VARCHAR(255) NOT NULL;

