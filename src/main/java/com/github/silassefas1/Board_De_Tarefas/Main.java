package com.github.silassefas1.Board_De_Tarefas;

import com.github.silassefas1.Board_De_Tarefas.persistence.migration.MigrationStrategy;

import java.sql.SQLException;

import static com.github.silassefas1.Board_De_Tarefas.persistence.config.ConnectionConfig.getConnection;


public class Main {

    public static void main(String[] args) throws SQLException {
        try(var connection = getConnection()){
            new MigrationStrategy(connection).executeMigration();
        }
    }

}
