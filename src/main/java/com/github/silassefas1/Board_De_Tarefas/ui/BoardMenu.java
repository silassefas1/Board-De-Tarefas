package com.github.silassefas1.Board_De_Tarefas.ui;

import com.github.silassefas1.Board_De_Tarefas.persistence.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class BoardMenu {

    public final BoardEntity entity;

    private final Scanner scanner = new Scanner(System.in);

    public void execute() {
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
                case 1 -> createBoard();
                case 2 -> moveCard();
                case 3 -> blockCard();
                case 4 -> unblockCard();
                case 5 -> cancelCard();
                case 6 -> showBoard();
                case 7 -> showColumnWithCard();
                case 8 -> showCard();
                case 9 -> System.out.println("Voltando Para o Menu Anterior");
                case 10 -> System.exit(0);
                default -> System.out.println("Opção Invalida, informe uma opção do menu");


            }
        }
    }

    private void createBoard() {
    }

    private void moveCard() {
    }

    private void blockCard() {
    }

    private void unblockCard() {
    }

    private void cancelCard() {
    }

    private void returnToPreviousMenu() {
    }

    private void showBoard() {
    }

    private void showColumnWithCard() {
        
    }

    private void showCard() {
        
    }
}
