package com.github.silassefas1.Board_De_Tarefas.service;

import com.github.silassefas1.Board_De_Tarefas.dto.BoardColumnInfoDTO;
import com.github.silassefas1.Board_De_Tarefas.exception.CardBlockedException;
import com.github.silassefas1.Board_De_Tarefas.exception.CardFinishedException;
import com.github.silassefas1.Board_De_Tarefas.exception.EntityNotFoundException;
import com.github.silassefas1.Board_De_Tarefas.persistence.dao.CardDAO;
import com.github.silassefas1.Board_De_Tarefas.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.github.silassefas1.Board_De_Tarefas.persistence.entity.enums.BoardColumnKindEnum.FINAL;

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
        try{
            var dao = new CardDAO(connection);
            var optional = dao.findById(cardId);
            var dto = optional.orElseThrow(
                    () -> new EntityNotFoundException("O Card de id %s não foi encontrado."
                            .formatted(cardId)));
            if(dto.blocked()){
                throw new CardBlockedException("O card esta bloqueado e precisa ser desbloqueado para ser movido.");
            }
            var currentColumn = boardColumnsInfo.stream()
                    .filter(boardColum -> boardColum.id().equals(dto.columnId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("O Card Selecionado não pertence a este Board, Impossivel mover."));
            if(currentColumn.kind().equals(FINAL)){
                throw new CardFinishedException("O card ja foi finalizado! ");
            }
            var nextColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.order() == currentColumn.order()+1)
                    .findFirst().orElseThrow(()->new IllegalStateException("O Card esta cancelado"));
            dao.moveToColumn(nextColumn.id(), cardId);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw new RuntimeException(ex);
        }
    }
}
