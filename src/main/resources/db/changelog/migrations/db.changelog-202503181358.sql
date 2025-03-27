--liquibase formatted sql
--changeset silas:202503181358
--comment: boards table create

CREATE TABLe BOARDS(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL

) ENGINE=InnoDB;

--rollback DROP TABLE BOARDS
