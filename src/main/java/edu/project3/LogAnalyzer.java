package edu.project3;

import edu.project3.input_arguments_parser.ArgumentsParser;
import edu.project3.input_arguments_parser.ParsedInput;
import edu.project3.log_sources.LogsSource;
import edu.project3.metrics.MetricsAndCollectorsHandler;
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
        //создание принтера
        //Выбор рендерера (типа таблиц) согласно аргументам из парсера (создать Renderer Factory)
    }

    private void run() {
        //считывание логов и пропуск через фильтры
        //рендер
        //вывод таблиц через принтер
    }

}
