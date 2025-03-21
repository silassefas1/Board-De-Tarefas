package com.github.silassefas1.Board_De_Tarefas.persistence.dao;

import com.github.silassefas1.Board_De_Tarefas.persistence.entity.BoardColumnEntity;
import com.mysql.cj.jdbc.StatementImpl;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class BoardColumnDAO {

    private final Connection conenction;

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException{
        var sql = "INSERT INTO BOARDS_COLUMNS (name,order,kind,boards_id) VALUE (?,?,?,?);";
        try(var stamente = conenction.prepareStatement(sql)){
            var i =1;
            stamente.setString(i++, entity.getName());
            stamente.setInt(i++, entity.getOrder());
            stamente.setString(i++,entity.getKind().name());
            stamente.setLong(i, entity.getBoard().getId());
            stamente.executeUpdate();
            if(stamente instanceof StatementImpl impl){
                entity.setId(impl.getLastInsertID());
            }
        }
        return entity;
    }


    public List<BoardColumnEntity> findByBoardId(Long id) throws SQLException {
        return null;
    }
}
