package com.github.silassefas1.Board_De_Tarefas.persistence;

import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
public class OffsetDateTimeConverter {

    public static OffsetDateTime toOffSetDateTime(final Timestamp value){
        return OffsetDateTime.ofInstant(value.toInstant(), UTC);
    }
}
