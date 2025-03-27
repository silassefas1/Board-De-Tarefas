package com.github.silassefas1.Board_De_Tarefas.ui;

import com.github.silassefas1.Board_De_Tarefas.persistence.entity.BoardColumnEntity;
import com.github.silassefas1.Board_De_Tarefas.persistence.entity.BoardEntity;
import com.github.silassefas1.Board_De_Tarefas.persistence.entity.CardEntity;
import com.github.silassefas1.Board_De_Tarefas.service.BoardColumnQueryService;
import com.github.silassefas1.Board_De_Tarefas.service.BoardQueryService;
import com.github.silassefas1.Board_De_Tarefas.service.CardQueryService;
import com.github.silassefas1.Board_De_Tarefas.service.CardService;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.Scanner;

import static com.github.silassefas1.Board_De_Tarefas.persistence.config.ConnectionConfig.getConnection;

@AllArgsConstructor
public class BoardMenu {

    private final BoardEntity entity;

    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException {
        System.out.printf("Bem Vindo ao Gerenciador do Board %s, Escolha a opção Desejada\n", entity.getId());
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
            scanner.nextLine();
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

    private void createCard() throws SQLException{
        var card = new CardEntity();
        System.out.println("Informe o Titulo do Card: ");
        card.setTitle(scanner.nextLine());
        System.out.println("Informe a Descrição do Card: ");
        card.setDescription(scanner.nextLine());
        card.setBoardColumn(entity.getInitialColumn());
        try(var connection = getConnection()) {
            new CardService(connection).insert(card);

        }

    }

    private void moveCard() throws SQLException {
        System.out.println("Informe o id do card que deseja mover para a proxima coluna: ");
        var cardId = scanner.nextLong();
        var boardColumnsInfo = entity.boardColumnInfo();
        try(var connection = getConnection()){
            new CardService(connection).moveToNexColumn(cardId,boardColumnsInfo);
            System.out.printf("Card %s movido com sucesso!\n", cardId);
        }catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void blockCard() throws SQLException{
        System.out.println("Informe o ID do card que será bloqueado: ");
        var cardId = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Informe o motivo do bloqueio do card: ");
        var reason = scanner.nextLine();
        var boardColumnsInfo = entity.boardColumnInfo();
        try(var connection = getConnection()){
            new CardService(connection).cardBlock(reason, cardId,boardColumnsInfo);
        }
    }

    private void unblockCard() throws SQLException{
    }

    private void cancelCard() throws SQLException{
        System.out.println("Informe o id do card que deseja cancelar: ");
        var cardId = scanner.nextLong();
        var cancelColumn = entity.getCancelColumn();
        var boardColumnsInfo = entity.boardColumnInfo();
        try(var connection = getConnection()){
            new CardService(connection).cardCancel(cardId,cancelColumn.getId(),boardColumnsInfo);
            System.out.printf("Card %s cancelado com sucesso\n", cardId );
        }catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void showBoard() throws SQLException{
        try(var connection = getConnection()) {
            var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
            optional.ifPresent(board->{
                System.out.printf("Board [%s, %s] \n", board.id(),board.name());
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
            System.out.printf("Escolha uma coluna do Board %s\n", entity.getName());
            entity.getBoardColumn().forEach(card ->
                    System.out.printf("%s - %s [%s]\n", card.getId(),card.getName(), card.getKind()));
            selectedColumn = scanner.nextLong();
        }
        try(var connection = getConnection()) {
            var column = new BoardColumnQueryService(connection).findById(selectedColumn);
            column.ifPresent(co ->{
                System.out.printf("Coluna %s tipo %s\n",co.getName(), co.getKind());
                co.getCards().forEach(card ->
                        System.out.printf("Card %s - %s\nDescrição: %s\n", card.getId(), card.getTitle(), card.getDescription() ));

            });
        }
    }

    private void showCard() throws SQLException {
        System.out.println("Informe o id do card que deseja visualizar ");
        var selectedCardId = scanner.nextLong();
        try(var connection = getConnection()){
            new CardQueryService(connection).findById(selectedCardId)
                    .ifPresentOrElse(
                    card ->{
                        System.out.printf("Card id: %s - %s\n",card.id(),card.title());
                        System.out.printf("Descrição: %s\n", card.description());
                        System.out.printf("Esta bloqueado? %s \nMotivo: %s\n",card.blocked(), card.blockReason());
                        System.out.printf("Bloqueado %s vezes\n", card.blockAmount() );
                        System.out.printf("Está no momento na coluna: %s - %s\n",card.columnId(),card.columnName());
                    },
                    ()->System.out.printf("Não existe um card com o id %s\n", selectedCardId));
        }
        
    }
}
