package edu.project3;

import edu.project3.input_arguments_parser.ArgumentsParser;
import edu.project3.input_arguments_parser.ParsedInput;
import edu.project3.log_sources.LogsSource;
import edu.project3.logs.NginxLogParser;
import edu.project3.metrics.MetricsAndCollectorsHandler;
import edu.project3.metrics.MetricsContainer;
import edu.project3.printer.Printer;
import edu.project3.renderers.TableRenderer;
import java.util.List;
import java.util.stream.Stream;
import static edu.project3.log_sources.LogSourceFactory.getLogsSource;
import static edu.project3.renderers.RendererFactory.getRenderer;

public class LogAnalyzer {

    private final ArgumentsParser argumentsParser;
    private Printer printer;
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

        printer = new Printer();
        renderer = getRenderer(settings.format());
    }

    private void run() {
        //получение логов
        Stream<String> logs = source.getLogs();
        //парсинг логов и пропуск через фильтры
        logs.forEach(log -> handler.processLog(logParser.parseLog(log)));
        //вывод таблиц через принтер
        List<MetricsContainer> tables = handler.getTables();
        StringBuilder report = new StringBuilder();

        tables.forEach(table -> report.append(renderer.getRenderedTable(table, table.getCols())).append("\n"));
        printer.printReport(report.toString());
    }

//    @SuppressWarnings("checkstyle:UncommentedMain")
//    public static void main(String[] args) {
//        //с консоли не запускал, только в идее аргументы менял
//        String input = String.join(" ", args);
//        //различные варианты ввода для тестов
//        //String input = "java -jar nginx-log-stats.jar --path https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs  --format markdown";
//        //String input = "java -jar nginx-log-stats.jar --path *  --format adoc";
//        //String input = "java -jar nginx-log-stats.jar --path *  --format markdown";
//        LogAnalyzer analyzer = new LogAnalyzer(input);
//
//        //при использовании стримов выигрыш по памяти примерно в 1.5 раза, по времени - нет
//    }
}
