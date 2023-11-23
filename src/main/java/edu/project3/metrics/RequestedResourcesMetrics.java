package edu.project3.metrics;

import edu.project3.statistic_collectors.ResourcesCollector;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestedResourcesMetrics extends MetricsContainer {
    private final static Logger LOGGER = LogManager.getLogger();

    public RequestedResourcesMetrics() {
        this.header = "Запрашиваемые ресурсы";
        collectors = List.of(new ResourcesCollector());
        setCols(2);
    }

    @Override
    public void build() {
        addRow(new MetricsRow(List.of("Ресурс", "Количество")));
        collectors.forEach(collector -> addRows(collector.getMetrics(cols)));

        LOGGER.info("Request resources table was built");
    }
}
