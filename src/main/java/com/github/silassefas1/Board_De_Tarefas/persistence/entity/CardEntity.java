package com.github.silassefas1.Board_De_Tarefas.persistence.entity;

import com.github.silassefas1.Board_De_Tarefas.dto.BoardColumnInfoDTO;
import com.github.silassefas1.Board_De_Tarefas.dto.CardDetailsDTO;
import com.github.silassefas1.Board_De_Tarefas.exception.CardStateException;
import com.github.silassefas1.Board_De_Tarefas.persistence.entity.enums.BoardColumnKindEnum;
import lombok.Data;

import java.util.List;

@Data
public class CardEntity {

    private Long id;
    private String title;
    private String description;
    private BoardColumnEntity boardColumn = new BoardColumnEntity();

    public BoardColumnInfoDTO getCurrentColumn(final CardDetailsDTO dto, final List<BoardColumnInfoDTO> boardColumnsInfo) {
        return boardColumnsInfo.stream()
                .filter(boardColumn -> boardColumn.id().equals(dto.columnId()))
                .findFirst()
                .orElseThrow(() ->
                        new CardStateException("O card não pertence a nenhuma coluna válida neste board. Não é possível movê-lo."));
    }

    public void verifyStatus(final CardDetailsDTO dto, final List<BoardColumnInfoDTO> boardColumnsInfo) {

        var currentColumn = boardColumnsInfo.stream()
                .filter(boardColumn -> boardColumn.id().equals(dto.columnId()))
                .findFirst()
                .orElseThrow(() ->
                        new CardStateException("O card não pertence a nenhuma coluna válida neste board. Não é possível movê-lo."));

        switch (currentColumn.kind()) {
            case FINAL -> throw new CardStateException("O card já foi finalizado e não pode ser alterado.");
            case CANCEL -> throw new CardStateException("O card está cancelado e não pode ser alterado.");
        }

        if (dto.blocked()) {
            throw new CardStateException("O card está bloqueado e precisa ser desbloqueado para ser alterado.");
        }
    }

    public void verifyStatus(final CardDetailsDTO dto) {

        List<BoardColumnInfoDTO> boardColumnsInfo = List.of(
                new BoardColumnInfoDTO(1L, 1, BoardColumnKindEnum.INITIAL),
                new BoardColumnInfoDTO(2L, 2, BoardColumnKindEnum.PENDING),
                new BoardColumnInfoDTO(3L, 3, BoardColumnKindEnum.FINAL),
                new BoardColumnInfoDTO(4L, 4, BoardColumnKindEnum.CANCEL)

        );

        var currentColumn = boardColumnsInfo.stream()
                .filter(boardColumn -> boardColumn.id().equals(dto.columnId()))
                .findFirst()
                .orElseThrow(() ->
                        new CardStateException("O card não pertence a nenhuma coluna válida neste board. Não é possível movê-lo."));

        switch (currentColumn.kind()) {
            case FINAL -> throw new CardStateException("O card já foi finalizado e não pode ser alterado.");
            case CANCEL -> throw new CardStateException("O card está cancelado e não pode ser alterado.");
        }

        var callerName = "none";
        Throwable t = new Throwable();
        StackTraceElement[] elements = t.getStackTrace();
        if (elements.length > 1) {
            StackTraceElement caller = elements[1];
            callerName = caller.getMethodName();
        }

        if(callerName.equals("cardUnblock")){
            if (!dto.blocked()) {
                throw new CardStateException("O card ja está desbloqueado, operação Invalida");
            }
        }else if(callerName.equals("cardBlock")){
            if (dto.blocked()) {
                throw new CardStateException("O card ja está bloqueado, Operação Invalida ");
            }

        }else{
            if (dto.blocked()) {
                throw new CardStateException("O card esta bloqueado e precisa ser desbloqueado para ser alterado ");
            }
        }
    }




}
