package edu.project3.metrics;

import edu.project3.statistic_collectors.ResourcesCollector;
import java.util.List;

public class RequestedResourcesMetrics extends MetricsContainer {
    public RequestedResourcesMetrics() {
        this.header = "Запрашиваемые ресурсы";
        collectors = List.of(new ResourcesCollector());
        setCols(2);
    }

    @Override
    public void build() {
        addRow(new MetricsRow(List.of("Ресурс", "Количество")));
        collectors.forEach(collector -> addRows(collector.getMetrics(cols)));
    }
}
