package com.github.silassefas1.Board_De_Tarefas.persistence.entity;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoardEntity {

    private Long id;
    private String name;

    @ToString.Exclude
    private List<BoardColumnEntity> boardColumn = new ArrayList<>();

}
