package com.github.silassefas1.Board_De_Tarefas.ui;

import com.github.silassefas1.Board_De_Tarefas.persistence.entity.BoardColumnEntity;
import com.github.silassefas1.Board_De_Tarefas.persistence.entity.BoardEntity;
import com.github.silassefas1.Board_De_Tarefas.persistence.entity.enums.BoardColumnKindEnum;
import com.github.silassefas1.Board_De_Tarefas.service.BoardQueryService;
import com.github.silassefas1.Board_De_Tarefas.service.BoardService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static com.github.silassefas1.Board_De_Tarefas.persistence.config.ConnectionConfig.getConnection;
import static com.github.silassefas1.Board_De_Tarefas.persistence.entity.enums.BoardColumnKindEnum.*;


public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException{
        System.out.println("Bem Vindo ao Gerenciador de Boards, Escolha a opção Desejada");
        var option = -1;
        while (true){
            System.out.println("1 - Criar novo Board");
            System.out.println("2 - Selecionar um Board");
            System.out.println("3 - Excluir um Board");
            System.out.println("4 - Sair");
            option = scanner.nextInt();
            switch (option){
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção Invalida, informe uma opção do menu");

                
            }
        }
    }

    private void createBoard()throws SQLException {
        var entity = new BoardEntity();
        System.out.println("Informe o nome do seu Board");
        entity.setName(scanner.next());

        System.out.println("Seu Board Tera colunas Alem das 3 padrões? se sim informe quantas, senão digite 0");
        try{
            var aditionalColumn = scanner.nextInt();
            if(aditionalColumn > 0){
                List<BoardColumnEntity> columns = new ArrayList<>();

                System.out.println("Informe o nome da coluna inicial do Board: ");
                var initialColumnName = scanner.next();
                var intialColumn = createColumn(initialColumnName, INITIAL, 0);
                columns.add(intialColumn);

                for (int i = 0; i < aditionalColumn; i++) {
                    System.out.println("Informe o nome das colunas de tarefas pendentes no Board: ");
                    var pendinColumnName = scanner.next();
                    var pendinColumn = createColumn(pendinColumnName, PENDING, i+1);
                    columns.add(pendinColumn);
                }

                System.out.println("Informe o nome da coluna final do Board: ");
                var finalColumnName = scanner.next();
                var finalColumn = createColumn(finalColumnName, FINAL, aditionalColumn+1);
                columns.add(finalColumn);

                System.out.println("Informe o nome da coluna cancelemento do Board: ");
                var cancelColumnName = scanner.next();
                var cancelColumn = createColumn(cancelColumnName, CANCEL, aditionalColumn+2);
                columns.add(cancelColumn);

                entity.setBoardColumn(columns);
                try(var connection = getConnection()) {
                    var service = new BoardService(connection);
                    service.insert(entity);

                }
            }


        }catch (InputMismatchException e){
            throw new InputMismatchException("Por favor Digite um valor valido: ");
        }

    }

    private void selectBoard() throws SQLException {
        System.out.println("Informe o id do seu Board que deseja selecionar: ");
        var boardId = scanner.nextLong();
        try(var connection = getConnection()){
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findById(boardId);
            if (optional.isPresent()) {
                var boardMenu = new BoardMenu(optional.get());
                boardMenu.execute();

            }else{
                System.out.printf("Não foi encontrado um board com id %s\n", boardId);
            }
        }
    }

    private void deleteBoard() throws SQLException {
        System.out.println("Informe o id do board a ser deletado: ");
        var id = scanner.nextLong();
        try(var connection = getConnection()) {
            var service = new BoardService(connection);
            if(service.delete(id)){
                System.out.printf("O board %s foi excluido\n", id);
            }else {
                System.out.printf("Board com ID %s Não Encontrado\n", id);
            }
        }
    }

    private BoardColumnEntity createColumn(final String name, final BoardColumnKindEnum kind, final int order){
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }
}
