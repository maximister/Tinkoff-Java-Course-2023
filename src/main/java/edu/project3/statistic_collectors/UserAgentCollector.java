package edu.project3.statistic_collectors;

import edu.project3.logs.log_structure.LogRequest;
import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAgentCollector extends StatisticsCollector {
    Map<String, Long> userAgentMap;
    private static final int TABLE_SIZE = 10;

    public UserAgentCollector() {
        collectorsName = "UserAgent";
        LOGGER.info(collectorsName + " was created");
        userAgentMap = new HashMap<>();
    }

    @Override
    public void processLog(NginxLogRecord log) {
        LogRequest request = log.request();
        userAgentMap.merge(request.agent(), 1L, Long::sum);

        processByNextCollector(log);
    }

    @Override
    public List<MetricsRow> getMetrics(int cols) {
        if (cols != 2) {
            throw new IllegalStateException(ROW_SIZE_ERROR);
        }

        return userAgentMap.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(TABLE_SIZE)
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
