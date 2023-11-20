package edu.project3.metrics;

import edu.project3.statistic_collectors.MethodsCollector;
import java.util.List;

public class HttpMethodsMetrics extends MetricsContainer {
    public HttpMethodsMetrics() {
        this.header = "Частота использования методов HTTP";
        collectors = List.of(new MethodsCollector());
        setCols(2);
    }

    @Override
    public void build() {
        addRow(new MetricsRow(List.of("Метод HTTP", "Количество")));
        collectors.forEach(collector -> addRows(collector.getMetrics(cols)));
    }
}
