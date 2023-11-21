package edu.project3.statistic_collectors;

import edu.project3.http_status_codes.HttpStatusCodesHandler;
import edu.project3.logs.log_structure.LogResponse;
import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusCollector extends StatisticsCollector {
    private final Map<Integer, Long> statusMap;
    private final HttpStatusCodesHandler httpStatusCodesHandler;
    private final static Path CODES_PATH
        = Path.of("src", "main", "java", "edu", "project3", "http_status_codes", "codes.txt");

    public StatusCollector() {
        collectorsName = "StatusCollector";
        LOGGER.info(collectorsName + " was created");
        httpStatusCodesHandler = new HttpStatusCodesHandler(CODES_PATH);
        statusMap = new HashMap<>();
    }

    @Override
    public void processLog(NginxLogRecord log) {
        LogResponse response = log.response();
        statusMap.merge(response.status(), 1L, Long::sum);

        processByNextCollector(log);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public List<MetricsRow> getMetrics(int cols) {
        if (cols != 3) {
            throw new IllegalStateException(ROW_SIZE_ERROR);
        }

        return statusMap.entrySet().stream()
            .map(entry ->
                MetricsRow.builder()
                    .values(List.of(Long.toString(entry.getKey()),
                        httpStatusCodesHandler.getHttpStatusDescription(entry.getKey()),
                        Long.toString(entry.getValue())
                    ))
                    .build()
            ).toList();
    }
}
