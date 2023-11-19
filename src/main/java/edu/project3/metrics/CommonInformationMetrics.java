package edu.project3.metrics;

import edu.project3.statistic_collectors.AverageBytesSentSizeCollector;
import java.util.List;

public class CommonInformationMetrics extends MetricsContainer {
    public CommonInformationMetrics() {
        this.header = "Общая информация";
        collectors = List.of(new AverageBytesSentSizeCollector());
        setCols(2);
    }


    //TODO: сли все будет работать, потом переделать как метод с реализацией тут,
    // которому на вход подают лишб заголовок таблицы
    @Override
    public void build() {
        addRow(new MetricsRow("Метрика", List.of("Значение")));
        //TODO: добавить даты и файлы

        collectors.forEach(collector -> addRows(collector.getMetrics(cols)));
    }
}
