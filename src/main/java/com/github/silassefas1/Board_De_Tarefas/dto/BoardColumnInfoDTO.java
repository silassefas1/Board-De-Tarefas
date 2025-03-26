package com.github.silassefas1.Board_De_Tarefas.dto;

import com.github.silassefas1.Board_De_Tarefas.persistence.entity.enums.BoardColumnKindEnum;

public record BoardColumnInfoDTO(Long id, int order, BoardColumnKindEnum kind) {
}
