package ru.tinkoff;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import ru.tinkoff.command_line.LogProcessorArguments;
import ru.tinkoff.command_line.OutputFormat;
import ru.tinkoff.formatting.LogReportRenderer;
import ru.tinkoff.formatting.MarkdownLogReportRenderer;
import ru.tinkoff.processing.LogProcessor;
import ru.tinkoff.processing.LogReport;

import java.io.IOException;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.concurrent.Callable;

@Command(name = "nginx-log-analyzer", description = "Analyze collection of nginx logs")
public class Main implements Callable<Integer> {

    @Option(names = {"--paths"}, description = "Paths for files with logs")
    private String[] paths;

    @Option(names = {"--from"}, description = "From date", paramLabel = "yyyy-MM-ddTHH:mm:ss.ns+hh:mm (offset)")
    private OffsetDateTime fromDate;

    @Option(names = {"--to"}, description = "End date", paramLabel = "yyyy-MM-ddTHH:mm:ss.ns+hh:mm (offset)")
    private OffsetDateTime toDate;

    @Option(names = {"--format"}, description = "Format for output report %nCandidates: ${COMPLETION-CANDIDATES}")
    private OutputFormat outputFormat;

    public static void main(String[] args) throws IOException {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        LogProcessorArguments arguments = new LogProcessorArguments(
                Arrays.asList(paths),
                fromDate,
                toDate,
                outputFormat
        );

        LogReport report = LogProcessor.processLogs(arguments);
        System.out.println(report);

        Path file = Path.of("src", "main", "resources", "reports", "report.md");
        LogReportRenderer logReportRenderer = new MarkdownLogReportRenderer();
        logReportRenderer.saveLogReport(report, file);
        System.out.println("Report was saved to " + file.toAbsolutePath());

        System.out.println(Arrays.toString(paths));
        System.out.println(fromDate);
        System.out.println(toDate);
        System.out.println(outputFormat);

        return 0;
    }
}
