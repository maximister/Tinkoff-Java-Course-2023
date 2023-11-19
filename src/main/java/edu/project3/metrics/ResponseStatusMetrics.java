package edu.project3.metrics;

import edu.project3.statistic_collectors.StatusCollector;
import java.util.List;

public class ResponseStatusMetrics extends MetricsContainer{
    @SuppressWarnings("checkstyle:MagicNumber") public ResponseStatusMetrics() {
        this.header = "Коды ответа";
        collectors = List.of(new StatusCollector());
        setCols(3);
    }

    @Override
    public void build() {
        addRow(new MetricsRow("Код", List.of("Имя", "Количество")));
        collectors.forEach(collector -> addRows(collector.getMetrics(cols)));
    }
}
