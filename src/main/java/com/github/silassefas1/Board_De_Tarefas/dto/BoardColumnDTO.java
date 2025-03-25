package com.github.silassefas1.Board_De_Tarefas.dto;

import com.github.silassefas1.Board_De_Tarefas.persistence.entity.enums.BoardColumnKindEnum;

public record BoardColumnDTO(Long id, String name, BoardColumnKindEnum kind, int cardsAmount) {
}
