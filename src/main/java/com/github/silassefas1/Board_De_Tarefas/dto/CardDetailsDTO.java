package com.github.silassefas1.Board_De_Tarefas.dto;

import java.time.OffsetDateTime;

public record CardDetailsDTO(Long id,
                                String title,
                                String description,
                                boolean blocked,
                                OffsetDateTime blockedAt,
                                String blockReason,
                                int blockAmount,
                                long columnId,
                                String columnName) {
}
