package edu.project3.metrics;

import edu.project3.statistic_collectors.MethodsCollector;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpMethodsMetrics extends MetricsContainer {
    private final static Logger LOGGER = LogManager.getLogger();

    public HttpMethodsMetrics() {
        this.header = "Частота использования методов HTTP";
        collectors = List.of(new MethodsCollector());
        setCols(2);
    }

    @Override
    public void build() {
        addRow(new MetricsRow(List.of("Метод HTTP", "Количество")));
        collectors.forEach(collector -> addRows(collector.getMetrics(cols)));

        LOGGER.info("Http methods table was built");
    }
}
