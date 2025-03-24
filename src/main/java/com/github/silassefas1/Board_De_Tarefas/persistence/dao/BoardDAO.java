package com.github.silassefas1.Board_De_Tarefas.persistence.dao;

import com.github.silassefas1.Board_De_Tarefas.persistence.entity.BoardEntity;
import com.mysql.cj.jdbc.StatementImpl;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@AllArgsConstructor
public class BoardDAO {

    private Connection conenction;

    public BoardEntity insert(final BoardEntity entity) throws SQLException{
        var sql = "INSERT INTO BOARDS (name) value (?);";
        try(var statment = conenction.prepareStatement(sql)){
            statment.setString(1, entity.getName());
            statment.executeUpdate();
            if(statment instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }
        return entity;
    }

    public void delete(final Long id) throws SQLException{
        var sql = "DELETE FROM BOARDS WHERE id = ?";
        try(var statment = conenction.prepareStatement(sql)){
            statment.setLong(1,id);
            statment.executeUpdate();
        }
    }

    public Optional<BoardEntity> findById(final Long id) throws SQLException{
        var sql = "SELECT id, name FROM BOARDS WHERE id = ?";
        try(var statment = conenction.prepareStatement(sql)){
            statment.setLong(1,id);
            statment.executeQuery();
            var resultSet = statment.getResultSet();

            if(resultSet.next()){
                var entity = new BoardEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                return Optional.of(entity);
            }
            return Optional.empty();
        }
    }

    public boolean exists(final Long id) throws SQLException{
        var sql = "SELECT 1 FROM BOARDS WHERE id = ?"; // se achar algo com o identificador ele vai retorna 1
        try(var statment = conenction.prepareStatement(sql)){
            statment.setLong(1,id);
            statment.executeQuery();
            return statment.getResultSet().next(); // verifica se o resultSet(que é o retorno do meu Statement) tem uma linha
        }

    }

}
