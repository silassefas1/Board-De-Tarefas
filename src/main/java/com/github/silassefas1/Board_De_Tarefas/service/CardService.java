package com.github.silassefas1.Board_De_Tarefas.service;

import com.github.silassefas1.Board_De_Tarefas.dto.BoardColumnInfoDTO;
import com.github.silassefas1.Board_De_Tarefas.exception.EntityNotFoundException;
import com.github.silassefas1.Board_De_Tarefas.persistence.dao.CardDAO;
import com.github.silassefas1.Board_De_Tarefas.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class CardService {

    private final Connection connection;



    public CardEntity insert(final CardEntity entity) throws SQLException {
        try{
            var dao = new CardDAO(connection);
            dao.insert(entity);
            connection.commit();
            return entity;
        }catch (SQLException ex){
            connection.rollback();
            throw ex;
        }

    }

    public void moveToNexColumn(final Long cardId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException{
        var cardEntity = new CardEntity();
        try{
            var dao = new CardDAO(connection);
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(() ->
                    new EntityNotFoundException("O Card de ID %d não foi encontrado.".formatted(cardId)));

            cardEntity.verifyStatus(dto, boardColumnsInfo);

            var currentColumn = cardEntity.getCurrentColumn(dto,boardColumnsInfo);

            var nextColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.order() == currentColumn.order() + 1)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Não há uma próxima coluna disponível para mover o card."));

            dao.moveToCancelColumn(nextColumn.id(), cardId);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw new RuntimeException(ex);
        }
    }

    public void cardCancel(final Long cardId, final Long cancelColumId, final String cancelReason) throws SQLException{
        try{
            var dao = new CardDAO(connection);
            var cardEntity = new CardEntity();
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O Card de id %s não foi encontrado."
                            .formatted(cardId)));
            cardEntity.verifyStatus(dto);
            dao.moveToCancelColumn(cancelColumId, cardId, cancelReason);
            connection.commit();
        }catch (SQLException ex) {
            connection.rollback();
            throw new RuntimeException(ex);
        }
    }

    public void cardBlock(final String reason, final Long cardId) throws SQLException{
        var cardEntity = new CardEntity();
        try{
            var dao = new CardDAO(connection);
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O Card de id %s não foi encontrado."));
            cardEntity.verifyStatus(dto);
            dao.cardBlock(reason,cardId);
            connection.commit();
        }catch (SQLException ex) {
            connection.rollback();
            throw new RuntimeException(ex);
        }
    }

    public void cardUnblock(final String reason, final Long cardId) throws SQLException {
        var cardEntity = new CardEntity();
        try {
            var dao = new CardDAO(connection);
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O Card de id %s não foi encontrado."));
            cardEntity.verifyStatus(dto);
            dao.cardUnblock(reason, cardId);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw new RuntimeException(ex);
        }
    }
}

