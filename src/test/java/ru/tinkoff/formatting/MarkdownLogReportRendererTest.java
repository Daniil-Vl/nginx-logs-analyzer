package ru.tinkoff.formatting;

import ru.tinkoff.rendering.LogReportRenderer;
import ru.tinkoff.rendering.MarkdownLogReportRenderer;

import java.nio.file.Path;

public class MarkdownLogReportRendererTest extends LogReportRendererTest {
    @Override
    public LogReportRenderer getInstance() {
        return new MarkdownLogReportRenderer();
    }

    @Override
    public Path getExpectedLogFile() {
        return TEMP_LOG_DIRECTORY.resolve("correct_markdown_report.md");
    }
}
