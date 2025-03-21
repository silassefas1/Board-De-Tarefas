package com.github.silassefas1.Board_De_Tarefas.persistence.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoardEntity {

    private Long id;
    private String name;
    private List<BoardColumnEntity> boardColumn = new ArrayList<>();

}
