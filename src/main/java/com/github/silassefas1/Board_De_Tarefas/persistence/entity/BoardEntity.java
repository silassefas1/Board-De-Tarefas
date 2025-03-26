package com.github.silassefas1.Board_De_Tarefas.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.github.silassefas1.Board_De_Tarefas.persistence.entity.enums.BoardColumnKindEnum.INITIAL;

@Data
public class BoardEntity {

    private Long id;
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BoardColumnEntity> boardColumn = new ArrayList<>();

    public BoardColumnEntity getInitialColumn(){
        return boardColumn.stream()
                .filter(bc -> bc.getKind().equals(INITIAL))
                .findFirst().orElseThrow();
    }

}
