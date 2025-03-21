package com.github.silassefas1.Board_De_Tarefas.service;

import com.github.silassefas1.Board_De_Tarefas.persistence.dao.BoardColumnDAO;
import com.github.silassefas1.Board_De_Tarefas.persistence.dao.BoardDAO;
import com.github.silassefas1.Board_De_Tarefas.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardQueryService {

    private final Connection connection;

    public Optional<BoardEntity> findById(final Long id) throws SQLException{
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        var optional = dao.findById(id);
        if (optional.isPresent()){
            var entity = optional.get();
            entity.setBoardColumn(boardColumnDAO.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }
    }

}
