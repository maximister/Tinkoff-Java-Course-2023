package edu.project3.metrics;

import edu.project3.logs.log_structure.LogRequest;
import edu.project3.logs.log_structure.NginxLogRecord;
import java.util.List;
import edu.project3.statistic_collectors.StatisticsCollector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RequestedResourcesCollectorTest {
    @Test
    @DisplayName("testing building tables")
    public void RequestedResourcesMetrics_shouldBuildCorrectTable() {
        RequestedResourcesMetrics metrics = new RequestedResourcesMetrics();

        //collecting information
        StatisticsCollector collector = metrics.getCollectors().get(0);
        getLogsList().forEach(collector::processLog);

        metrics.build();

        assertThat(metrics.getTable()).isEqualTo(getTable());
    }
    private static List<NginxLogRecord> getLogsList() {
        List<NginxLogRecord> logs = List.of(
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .url("test").build())
                .build(),
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .url("test1").build())
                .build(),
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .url("test").build())
                .build()
        );

        return logs;
    }

    private static List<MetricsRow> getTable() {
        return List.of(
            new MetricsRow(List.of("Ресурс", "Количество")),
            new MetricsRow(List.of("test", "2")),
            new MetricsRow(List.of("test1", "1"))
        );
    }
}
