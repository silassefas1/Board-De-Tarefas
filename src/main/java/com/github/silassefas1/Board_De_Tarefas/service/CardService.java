package com.github.silassefas1.Board_De_Tarefas.service;

import com.github.silassefas1.Board_De_Tarefas.persistence.dao.CardDAO;
import com.github.silassefas1.Board_De_Tarefas.persistence.entity.CardEntity;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

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
}
