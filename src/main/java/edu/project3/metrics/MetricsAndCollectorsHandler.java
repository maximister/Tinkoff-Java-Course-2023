package edu.project3.metrics;

import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.statistic_collectors.StatisticsCollector;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class MetricsAndCollectorsHandler {
    //TODO: проверить, что временной фильтр первый
    private List<MetricsContainer> tables;
    private List<StatisticsCollector> collectors;

    public MetricsAndCollectorsHandler(OffsetDateTime from, OffsetDateTime to) {
        tables = List.of(
            new CommonInformationMetrics(from, to),
            new RequestedResourcesMetrics(),
            new ResponseStatusMetrics()
        );
    }

    public void buildChainOfCollectors() {
        if (collectors == null) {
            getCollectors();
        }

        for (int i = 0; i < collectors.size() - 1; i++) {
            collectors.get(i).setNextCollector(collectors.get(i + 1));
        }
    }

    private void getCollectors() {
        collectors = new ArrayList<>();
        tables.forEach(table -> collectors.addAll(table.getCollectors()));
    }

    public void processLog(NginxLogRecord log) {
        if (collectors != null && !collectors.isEmpty()) {
            collectors.get(0).processLog(log);
        }
    }

    public List<MetricsContainer> getTables() {
        tables.forEach(MetricsContainer::build);
        return tables;
    }
}
