package ru.tinkoff.processing;

import org.junit.jupiter.api.Test;
import ru.tinkoff.command_line.LogProcessorArguments;
import ru.tinkoff.command_line.OutputFormat;
import java.io.IOException;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;

class LogProcessorTest {

    @Test
    void processLogs() throws IOException {
        LogProcessorArguments arguments = new LogProcessorArguments(
                List.of("src/test/resources/logs/logs_example.txt"),
                null,
                null,
                OutputFormat.MARKDOWN
        );

        LogReport actual = LogProcessor.processLogs(arguments);

        PriorityQueue<Map.Entry<String, Integer>> expectedRequestedResources =
                new PriorityQueue<>((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        expectedRequestedResources.addAll(Map.of(
                "/downloads/product_1", 1,
                "/downloads/product_2", 2
        ).entrySet());

        PriorityQueue<Map.Entry<Integer, Integer>> expectedStatusCodes =
                new PriorityQueue<>((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        expectedStatusCodes.addAll(Map.of(
                404, 1,
                304, 2
        ).entrySet());

        LogReport expected = new LogReport(
                List.of(Path.of("src/test/resources/logs/logs_example.txt").toString()),
                OffsetDateTime.MIN,
                OffsetDateTime.MAX,
                3,
                112,
                expectedRequestedResources,
                expectedStatusCodes
        );

        assertThat(actual).isEqualTo(expected);
    }
}
