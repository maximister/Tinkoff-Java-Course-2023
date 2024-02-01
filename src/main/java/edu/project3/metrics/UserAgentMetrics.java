package edu.project3.metrics;

import edu.project3.statistic_collectors.UserAgentCollector;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserAgentMetrics extends MetricsContainer {
    private final static Logger LOGGER = LogManager.getLogger();

    public UserAgentMetrics() {
        this.header = "Часто используемые user agents";
        collectors = List.of(new UserAgentCollector());
        setCols(2);
    }

    @Override
    public void build() {
        addRow(new MetricsRow(List.of("User agent", "Количество")));
        collectors.forEach(collector -> addRows(collector.getMetrics(cols)));

        LOGGER.info("User agents table eas built");
    }
}
