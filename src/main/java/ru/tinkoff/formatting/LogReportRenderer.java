package ru.tinkoff.formatting;

import ru.tinkoff.http.HttpStatusCode;
import ru.tinkoff.processing.LogReport;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public abstract class LogReportRenderer {
    protected final String lineSeparator = System.lineSeparator();
    protected String separator;
    protected String headerSymbols;

    protected abstract String renderTable(List<String> headers, List<List<String>> rows);

    private String renderHeader(String header) {
        return headerSymbols + " " + header + lineSeparator;
    }

    private String renderGeneralInformation(LogReport logReport) {
        return renderHeader("Общая информация")
                + renderTable(
                List.of("Метрика", "Значение"),
                List.of(
                        List.of("Файл(-ы)", String.join(", ", logReport.files())),
                        List.of("Начальная дата", logReport.startDate() != null ? logReport.startDate().toString() : "-"),
                        List.of("Конечная дата", logReport.endDate() != null ? logReport.endDate().toString() : "-"),
                        List.of("Количество запросов", logReport.numberOfRequests().toString()),
                        List.of("Средний размер ответа", logReport.averageResponseSize().toString())
                )
        );
    }

    private String renderRequestedResources(LogReport logReport) {
        List<List<String>> rows = new ArrayList<>();

        var requestedSources = logReport.requestedSources();

        while (!requestedSources.isEmpty()) {
            var entry = requestedSources.poll();
            rows.add(List.of(entry.getKey(), entry.getValue().toString()));
        }

        return renderHeader("Запрашиваемые ресурсы")
                + renderTable(
                List.of("Ресурс", "Количество"),
                rows
        );
    }

    private String renderStatusCodes(LogReport logReport) {
        List<List<String>> rows = new ArrayList<>();

        PriorityQueue<Map.Entry<Integer, Integer>> statusCodes = logReport.statusCodes();

        while (!statusCodes.isEmpty()) {
            Map.Entry<Integer, Integer> entry = statusCodes.poll();
            rows.add(
                    List.of(
                            entry.getKey().toString(),
                            HttpStatusCode.getHttpStatusCode(entry.getKey()).toString(),
                            entry.getValue().toString()
                    )
            );
        }

        return renderHeader("Коды ответа")
                + renderTable(
                List.of("Код", "Статус", "Количество"),
                rows
        );
    }

    public void saveLogReport(LogReport logReport, Path file) throws IOException {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile(), StandardCharsets.UTF_8))
        ) {
            writer.write(renderGeneralInformation(logReport));
            writer.write(renderRequestedResources(logReport));
            writer.write(renderStatusCodes(logReport));
        }
    }
}
