package edu.project3.collectors;

import edu.project3.logs.log_structure.LogRequest;
import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import edu.project3.statistic_collectors.ResourcesCollector;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResourcesCollectorTest {
    @Test
    @DisplayName("testing collecting process")
    public void ResourcesCollectingProcessTest() {
        ResourcesCollector resourcesCollector = new ResourcesCollector();

        getLogsList().forEach(resourcesCollector::processLog);

        assertThat(resourcesCollector.getMetrics(2)).isEqualTo(getMetricsList());

            //required table row size is not equal to this metrics row size
        assertThrows(IllegalStateException.class, () -> resourcesCollector.getMetrics(1));
    }

    private static List<NginxLogRecord> getLogsList() {
        List<NginxLogRecord> logs = List.of(
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .url("test1")
                    .build())
                .build(),
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .url("test2")
                    .build())
                .build(),
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .url("test1")
                    .build())
                .build()
        );

        return logs;
    }

    private static List<MetricsRow> getMetricsList() {
        return List.of(
            new MetricsRow(List.of("test2", "1")),
            new MetricsRow(List.of("test1", "2"))
        );
    }
}
