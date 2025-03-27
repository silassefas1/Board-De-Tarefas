package com.github.silassefas1.Board_De_Tarefas.dto;

import java.time.OffsetDateTime;

public record CardDetailsDTO(Long id,
                                String title,
                                String description,
                                String cancel_reason,
                                boolean blocked,
                                OffsetDateTime blockedAt,
                                String blockReason,
                                int blockAmount,
                                long columnId,
                                String columnName) {
}
