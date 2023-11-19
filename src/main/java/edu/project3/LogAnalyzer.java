package edu.project3;

import edu.project3.log_sources.LogsSource;
import edu.project3.metrics.MetricsAndCollectorsHandler;

public class LogAnalyzer {

    //добавить парсер аргументов
//    private ArgumentsParser argumentsParser;
//    private TablePrinter printer;
//    private Renderer renderer;
    private MetricsAndCollectorsHandler handler;
    private LogsSource source;

    public LogAnalyzer(String input) {
        //парсинг аргументов
//        argumentsParser = new ArgumentParser(input);
        build();
        run();
    }

    private void build() {
        //Log Source Factory задает Log Suorce согласно агрументам парсера
        //создание хэндлера
        //передача времени в хэндлер
        //Создание цепи фильтров
        //создание принтера
        //Выбор рендерера (типа таблиц) согласно аргументам из парсера (создать Renderer Factory)
    }

    private void run() {
        //считывание логов и пропуск через фильтров
        //рендер
        //вывод таблиц через принтер
    }

}
