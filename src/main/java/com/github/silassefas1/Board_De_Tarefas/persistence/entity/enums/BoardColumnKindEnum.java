package com.github.silassefas1.Board_De_Tarefas.persistence.entity.enums;

import java.util.stream.Stream;

public enum BoardColumnKindEnum {

    INITIAL, FINAL, CANCEL, PENDING;

    public static BoardColumnKindEnum findByName(final String name){
        return Stream.of(BoardColumnKindEnum.values())
                .filter(b-> b.name().equals(name))
                .findFirst().orElseThrow();
    }
}
