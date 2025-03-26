package com.github.silassefas1.Board_De_Tarefas.persistence.dao;

import com.github.silassefas1.Board_De_Tarefas.dto.CardDetailsDTO;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static com.github.silassefas1.Board_De_Tarefas.persistence.converter.OffsetDateTimeConverter.toOffSetDateTime;
import static java.util.Objects.nonNull;

@AllArgsConstructor
public class CardDAO {

    private Connection connection;

    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException {
        var sql= """
                SELECT card.id,
                    card.title,
                    card.description,
                    block.blocked_at,
                    block.blocked_reason,
                    card.board_column_id,
                    boardcolumn.name,
                    (SELECT COUNT(sub_block.id)
                            FROM BLOCKS sub_block
                        WHERE sub_block.card.id = card.id) blocks_amount
                FROM CARDS card
                LEFT JOIN BLOCKS block
                    ON card.id = block.cards_id
                AND block.unblocked_at IS NULL
                INNER JOIN BOARD_COLUMNS boardcolumn
                    ON boardcolumn.id = card.board_column_id
                WHERE id = ?;
                """;

        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if(resultSet.next()){
                var dto = new CardDetailsDTO(
                        resultSet.getLong("card_id"),
                        resultSet.getString("card.title"),
                        resultSet.getString("card.desciption"),
                        nonNull(resultSet.getString("block.blocked_reason")),
                        toOffSetDateTime(resultSet.getTimestamp("block.blocked_at,")),
                        resultSet.getString("block.blocked_reason"),
                        resultSet.getInt("blocks_amount"),
                        resultSet.getLong("card.board_column_id"),
                        resultSet.getString("boardcolumn.name,")
                );
                return Optional.of(dto);
            }

        }

        return Optional.empty();
    }
}
