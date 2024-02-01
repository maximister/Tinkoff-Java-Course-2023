package edu.project3.renderer;

import edu.project3.metrics.MetricsContainer;
import edu.project3.metrics.MetricsRow;
import edu.project3.statistic_collectors.MethodsCollector;
import java.util.List;

public class TestMetricsContainer extends MetricsContainer {
    public TestMetricsContainer() {
        this.header = "Test Table";
        collectors = null;
        setCols(2);
    }

    @Override
    public void build() {
        addRow(new MetricsRow(List.of("test metrics", "test value")));
        addRow(MetricsRow.builder()
            .values(List.of("metric", "value"))
            .build());
    }
}
