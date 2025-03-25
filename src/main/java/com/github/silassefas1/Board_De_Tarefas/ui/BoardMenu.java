package com.github.silassefas1.Board_De_Tarefas.ui;

import com.github.silassefas1.Board_De_Tarefas.persistence.entity.BoardColumnEntity;
import com.github.silassefas1.Board_De_Tarefas.persistence.entity.BoardEntity;
import com.github.silassefas1.Board_De_Tarefas.service.BoardColumnQueryService;
import com.github.silassefas1.Board_De_Tarefas.service.BoardQueryService;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.Scanner;

import static com.github.silassefas1.Board_De_Tarefas.persistence.config.ConnectionConfig.getConnection;

@AllArgsConstructor
public class BoardMenu {

    private final BoardEntity entity;

    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException {
        System.out.println("Bem Vindo ao Gerenciador de Boards, Escolha a opção Desejada");
        var option = -1;
        while (option != 9){
            System.out.println("1 - Criar um Card");
            System.out.println("2 - Mover um Card");
            System.out.println("3 - Bloquear um Card");
            System.out.println("4 - Desbloquear um Card");
            System.out.println("5 - Cancelar um Card");
            System.out.println("6 - Ver Board");
            System.out.println("7 - Ver Coluna com Card");
            System.out.println("8 - Ver Card");
            System.out.println("9 - Voltar Para o Menu Anterior");
            System.out.println("10 - Sair");

            option = scanner.nextInt();
            switch (option){
                case 1 -> createCard();
                case 2 -> moveCard();
                case 3 -> blockCard();
                case 4 -> unblockCard();
                case 5 -> cancelCard();
                case 6 -> showBoard();
                case 7 -> showColumn();
                case 8 -> showCard();
                case 9 -> System.out.println("Voltando Para o Menu Anterior");
                case 10 -> System.exit(0);
                default -> System.out.println("Opção Invalida, informe uma opção do menu");


            }
        }
    }

    private void createCard() {
    }

    private void moveCard() {
    }

    private void blockCard() {
    }

    private void unblockCard() {
    }

    private void cancelCard() {
    }

    private void showBoard() throws SQLException{
        try(var connection = getConnection()) {
            var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
            optional.ifPresent(board->{
                System.out.printf("Board [%s, %s]", board.id(),board.name());
                board.column().forEach(card -> {
                    System.out.printf("Column [%s] tipo: [%s] tem %s cards \n", card.name(), card.kind(),card.cardsAmount());
                });
            });


        }
    }

    private void showColumn() throws SQLException {
        var columnsIds = entity.getBoardColumn().stream().map(BoardColumnEntity::getId).toList();
        var selectedColumn = -1L;
        while(!columnsIds.contains(selectedColumn)){
            System.out.printf("Escolha uma coluna do Board %s \n", entity.getName());
            entity.getBoardColumn().forEach(card ->
                    System.out.printf("%s - %s [%s]\n", card.getId(),card.getName(), card.getKind()));
            selectedColumn = scanner.nextLong();
        }
        try(var connection = getConnection()) {
            var column = new BoardColumnQueryService(connection).findById(selectedColumn);
            column.ifPresent(co ->{
                System.out.printf("Coluna %s tipo %s\n",co.getName(), co.getKind());
                co.getCards().forEach(card ->
                        System.out.printf("Card %s - %s\nDescrição: %s ", card.getId(), card.getTitle(), card.getDescription() ));

            });
        }
    }

    private void showCard() {
        
    }
}
