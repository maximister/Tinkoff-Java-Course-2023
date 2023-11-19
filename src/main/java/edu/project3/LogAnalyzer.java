package edu.project3;

import edu.project3.input_arguments_parser.ArgumentsParser;
import edu.project3.input_arguments_parser.ParsedInput;
import edu.project3.log_sources.LogsSource;
import edu.project3.logs.NginxLogParser;
import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsAndCollectorsHandler;
import edu.project3.metrics.MetricsContainer;
import edu.project3.renderers.MarkdownRenderer;
import edu.project3.renderers.TableRenderer;
import java.util.List;
import static edu.project3.log_sources.LogSourceFactory.getLogsSource;

public class LogAnalyzer {

    //input example
    //String input = "java -jar nginx-log-stats.jar --path logs/2023* --from 2023-08-31 --format markdown";
    //добавить парсер аргументов
    private ArgumentsParser argumentsParser;
//    private TablePrinter printer;
//    private Renderer renderer;
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
        //Log Source Factory задает Log Suorce согласно агрументам парсера
        source = getLogsSource(settings.path());
        //создание хэндлера
        //передача времени в хэндлер
        handler = new MetricsAndCollectorsHandler(settings.from(), settings.to());
        //Создание цепи фильтров
        handler.buildChainOfCollectors();
        //создание парсера
        logParser = new NginxLogParser();
        //создание принтера
        //Выбор рендерера (типа таблиц) согласно аргументам из парсера (создать Renderer Factory)
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
        TableRenderer renderer = new MarkdownRenderer();
        System.out.println(renderer.getRenderedTable(tables.get(0), tables.get(0).getCols()));
        //tables.forEach(System.out::println);
    }

    public static void main(String[] args) {
        //java -jar nginx-log-stats.jar --path logs/2023* --from 2023-08-31 --format markdown
        String input = "java -jar nginx-log-stats.jar --path *  --format markdown";
        LogAnalyzer analyzer = new LogAnalyzer(input);
    }

}
