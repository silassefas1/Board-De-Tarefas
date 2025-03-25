package com.github.silassefas1.Board_De_Tarefas.service;

import com.github.silassefas1.Board_De_Tarefas.persistence.dao.BoardColumnDAO;
import com.github.silassefas1.Board_De_Tarefas.persistence.entity.BoardColumnEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardColumnQueryService {

    private final Connection connection;

    public Optional<BoardColumnEntity> findById(final Long id) throws SQLException {
        var dao = new BoardColumnDAO(connection);
        return dao.findById(id);

    }
}
