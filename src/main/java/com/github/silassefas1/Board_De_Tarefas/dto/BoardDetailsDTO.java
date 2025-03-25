package com.github.silassefas1.Board_De_Tarefas.dto;

import java.util.List;

public record BoardDetailsDTO(Long id, String name, List<BoardColumnDTO> column) {
}
