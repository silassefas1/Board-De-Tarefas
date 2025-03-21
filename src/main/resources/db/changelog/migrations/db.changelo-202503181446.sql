--liquibase formatted sql
--changeset silas:202503181358
--comment: boards table create

CREATE TABLe CARDS(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    board_column_id BIGINT NOT NULL,
    CONSTRAINT boards_columns_cards_fk FOREIGN KEY (board_column_id) REFERENCES BOARDS(id) ON DELETE CASCADE

) ENGINE=InnoDB;

--rollback DROP TABLE CARDS
