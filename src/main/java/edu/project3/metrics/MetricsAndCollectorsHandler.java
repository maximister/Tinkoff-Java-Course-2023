package edu.project3.metrics;

import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.statistic_collectors.StatisticsCollector;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MetricsAndCollectorsHandler {
    private final List<MetricsContainer> tables;
    private List<StatisticsCollector> collectors;
    private boolean wasBuild;
    private final static Logger LOGGER = LogManager.getLogger();

    public MetricsAndCollectorsHandler(OffsetDateTime from, OffsetDateTime to, String sources) {
        tables = List.of(
            new CommonInformationMetrics(from, to, sources),
            new RequestedResourcesMetrics(),
            new ResponseStatusMetrics(),
            new HttpMethodsMetrics(),
            new UserAgentMetrics()
        );

        LOGGER.info("Metrics and Collectors Handler was created");
        wasBuild = false;
    }

    public void buildChainOfCollectors() {
        if (collectors == null) {
            getCollectors();
        }

        for (int i = 0; i < collectors.size() - 1; i++) {
            collectors.get(i).setNextCollector(collectors.get(i + 1));
        }

        LOGGER.info("Collectors Chain was built");
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
        if (!wasBuild) {
            tables.forEach(MetricsContainer::build);
            wasBuild = true;
        }
        return tables;
    }
}
