package edu.project3.statistic_collectors;

import edu.project3.logs.log_structure.LogRequest;
import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourcesCollector extends StatisticsCollector {

    private final Map<String, Long> resourceMap;

    public ResourcesCollector() {
        collectorsName = "ResourcesCollector";
        LOGGER.info(collectorsName + " was created");
        resourceMap = new HashMap<>();
    }

    @Override
    public void processLog(NginxLogRecord log) {
        LogRequest request = log.request();
        resourceMap.merge(request.url(), 1L, Long::sum);

        processByNextCollector(log);
    }

    @Override
    public List<MetricsRow> getMetrics(int cols) {
        if (cols != 2) {
            throw new IllegalStateException(ROW_SIZE_ERROR);
        }

        return resourceMap.entrySet().stream()
            .map(entry -> MetricsRow.builder()
                .values(List.of(entry.getKey(), Long.toString(entry.getValue())))
                .build())
            .toList();
    }
}
