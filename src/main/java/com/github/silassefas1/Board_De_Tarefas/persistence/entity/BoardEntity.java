package com.github.silassefas1.Board_De_Tarefas.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.github.silassefas1.Board_De_Tarefas.persistence.entity.enums.BoardColumnKindEnum.CANCEL;
import static com.github.silassefas1.Board_De_Tarefas.persistence.entity.enums.BoardColumnKindEnum.INITIAL;

@Data
public class BoardEntity {

    private Long id;
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BoardColumnEntity> boardColumn = new ArrayList<>();

    public BoardColumnEntity getInitialColumn(){
        return getFilteredColumn(boardColumnc -> boardColumnc.getKind().equals(INITIAL));
    }

    public BoardColumnEntity getCancelColumn(){
        return getFilteredColumn(boardColumnc -> boardColumnc.getKind().equals(CANCEL));
    }

    private BoardColumnEntity getFilteredColumn(Predicate<BoardColumnEntity> filter){
        return boardColumn.stream().filter(filter).findFirst().orElseThrow();
    }

}
