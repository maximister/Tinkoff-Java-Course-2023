package edu.project3;

import edu.project3.input_arguments_parser.ArgumentsParser;
import edu.project3.input_arguments_parser.ParsedInput;
import edu.project3.log_sources.LogsSource;
import edu.project3.logs.NginxLogParser;
import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsAndCollectorsHandler;
import edu.project3.metrics.MetricsContainer;
import edu.project3.printer.TablePrinter;
import edu.project3.renderers.TableRenderer;
import java.util.List;
import static edu.project3.log_sources.LogSourceFactory.getLogsSource;
import static edu.project3.renderers.RendererFactory.getRenderer;

public class LogAnalyzer {

    private ArgumentsParser argumentsParser;
    private TablePrinter printer;
    private TableRenderer renderer;
    private ParsedInput settings;
    private MetricsAndCollectorsHandler handler;
    private NginxLogParser logParser;
    private LogsSource source;

    public LogAnalyzer(String input) {

        argumentsParser = new ArgumentsParser(input);
        build();
        run();
    }

    private void build() {
        settings = argumentsParser.getParsedInput();
        source = getLogsSource(settings.path());

        handler = new MetricsAndCollectorsHandler(settings.from(), settings.to(), source.getSources());
        handler.buildChainOfCollectors();
        logParser = new NginxLogParser();

        printer = new TablePrinter();
        renderer = getRenderer(settings.format());
    }

    private void run() {
        //получение и парсинг логов
        List<String> logs = source.getLogs();
        List<NginxLogRecord> parsedLogs = logParser.parseLogs(logs);
        //считывание логов и пропуск через фильтры
        parsedLogs.forEach(log -> handler.processLog(log));
        //рендер
        //вывод таблиц через принтер
        List<MetricsContainer> tables = handler.getTables();
        tables.forEach(table -> printer.printTable(
            renderer.getRenderedTable(table, table.getCols())
        ));
    }

    @SuppressWarnings("checkstyle:UncommentedMain")
    public static void main(String[] args) {
        //java -jar nginx-log-stats.jar --path logs/2023* --from 2023-08-31 --format markdown
        //String input = "java -jar nginx-log-stats.jar --path https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs  --format markdown";
        //String input = "java -jar nginx-log-stats.jar --path *  --format adoc";
        String input = "java -jar nginx-log-stats.jar --path *  --format markdown";
        LogAnalyzer analyzer = new LogAnalyzer(input);
    }

}
