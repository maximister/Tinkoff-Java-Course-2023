package edu.project3.statistic_collectors;

import edu.project3.logs.log_structure.LogRequest;
import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodsCollector extends StatisticsCollector {
    Map<String, Long> methodsMap;

    public MethodsCollector() {
        collectorsName = "ProtocolCollector";
        LOGGER.info(collectorsName + " was created");
        methodsMap = new HashMap<>();
    }

    @Override
    public void processLog(NginxLogRecord log) {
        LogRequest request = log.request();
        methodsMap.merge(request.method(), 1L, Long::sum);

        processByNextCollector(log);
    }

    @Override
    public List<MetricsRow> getMetrics(int cols) {
        if (cols != 2) {
            throw new IllegalStateException(ROW_SIZE_ERROR);
        }

        return methodsMap.entrySet().stream()
            .map(entry ->
                MetricsRow.builder()
                    .values(List.of(
                        entry.getKey(),
                        entry.getValue().toString()
                    ))
                    .build()
            ).toList();
    }
}
