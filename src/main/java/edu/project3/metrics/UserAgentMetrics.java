package edu.project3.metrics;

import edu.project3.statistic_collectors.UserAgentCollector;
import java.util.List;

public class UserAgentMetrics extends MetricsContainer {
    public UserAgentMetrics() {
        this.header = "Часто используемые user agents";
        collectors = List.of(new UserAgentCollector());
        setCols(2);
    }

    @Override
    public void build() {
        addRow(new MetricsRow(List.of("User agent", "Количество")));
        collectors.forEach(collector -> addRows(collector.getMetrics(cols)));
    }
}
