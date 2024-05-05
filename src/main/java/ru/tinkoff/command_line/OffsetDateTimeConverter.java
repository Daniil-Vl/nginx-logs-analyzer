package ru.tinkoff.command_line;

import picocli.CommandLine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeConverter implements CommandLine.ITypeConverter<LocalDateTime> {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime convert(String s) throws Exception {
        return LocalDateTime.parse(s, dateTimeFormatter);
//        return OffsetDateTime.parse(s, dateTimeFormatter);
    }
}
