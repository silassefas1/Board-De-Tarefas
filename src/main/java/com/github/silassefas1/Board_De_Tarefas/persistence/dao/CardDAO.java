package com.github.silassefas1.Board_De_Tarefas.persistence.dao;

import com.github.silassefas1.Board_De_Tarefas.dto.CardDetailsDTO;
import com.github.silassefas1.Board_De_Tarefas.persistence.entity.CardEntity;
import com.mysql.cj.jdbc.StatementImpl;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Optional;

import static com.github.silassefas1.Board_De_Tarefas.persistence.converter.OffsetDateTimeConverter.toOffSetDateTime;
import static com.github.silassefas1.Board_De_Tarefas.persistence.converter.OffsetDateTimeConverter.toTimeStamp;
import static java.util.Objects.nonNull;

@AllArgsConstructor
public class CardDAO {

    private Connection connection;

    public CardEntity insert(final CardEntity entity) throws SQLException{
        var sql = "INSERT INTO CARDS (title, description, board_column_id) VALUE (?, ?, ?);";
        try(var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setString(i++, entity.getTitle());
            statement.setString(i++, entity.getDescription());
            statement.setLong(i, entity.getBoardColumn().getId());
            statement.executeUpdate();
            if (statement instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }
        return entity;
    }

    public void moveToColumn(final Long columnId, final Long cardId) throws SQLException{
        var sql = "UPDATE CARDS SET board_column_id = ? WHERE id = ?;";
        try(var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setLong(i ++, columnId);
            statement.setLong(i, cardId);
            statement.executeUpdate();
        }
    }

    public void cardBlock(final String reason, final Long cardId)throws SQLException {
        var sql = "INSERT INTO BLOCKS (blocked_at, blocked_reason, card_id) VALUES (?, ?, ?);";
        try(var statement = connection.prepareStatement(sql)){
            var i = 1;
            statement.setTimestamp(i ++, toTimeStamp(OffsetDateTime.now()) );
            statement.setString(i++, reason);
            statement.setLong(i,cardId);
            statement.executeUpdate();
        }
    }



    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException {
        var sql =
                """
                SELECT card.id,
                       card.title,
                       card.description,
                       blocks.blocked_at,
                       blocks.blocked_reason,
                       card.board_column_id,
                       boards_columns.name,
                       (SELECT COUNT(sub_board.id)
                               FROM BLOCKS sub_board
                              WHERE sub_board.card_id = card.id) blocks_amount
                  FROM CARDS card
                  LEFT JOIN BLOCKS blocks
                    ON card.id = blocks.card_id
                   AND blocks.unblocked_at IS NULL
                 INNER JOIN BOARDS_COLUMNS boards_columns
                    ON boards_columns.id = card.board_column_id
                  WHERE card.id = ?;
                """;
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if(resultSet.next()){
                var dto = new CardDetailsDTO(
                        resultSet.getLong("card.id"),
                        resultSet.getString("card.title"),
                        resultSet.getString("card.description"),
                        nonNull(resultSet.getString("blocks.blocked_reason")),
                        toOffSetDateTime(resultSet.getTimestamp("blocks.blocked_at")),
                        resultSet.getString("blocks.blocked_reason"),
                        resultSet.getInt("blocks_amount"),
                        resultSet.getLong("card.board_column_id"),
                        resultSet.getString("boards_columns.name")
                );
                return Optional.of(dto);
            }

        }

        return Optional.empty();
    }
}
