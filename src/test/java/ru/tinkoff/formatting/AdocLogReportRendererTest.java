package ru.tinkoff.formatting;

import ru.tinkoff.rendering.AdocLogReportRenderer;
import ru.tinkoff.rendering.LogReportRenderer;

import java.nio.file.Path;

public class AdocLogReportRendererTest extends LogReportRendererTest {
    @Override
    public LogReportRenderer getInstance() {
        return new AdocLogReportRenderer();
    }

    @Override
    public Path getExpectedLogFile() {
        return TEMP_LOG_DIRECTORY.resolve("correct_adoc_report.adoc");
    }
}
