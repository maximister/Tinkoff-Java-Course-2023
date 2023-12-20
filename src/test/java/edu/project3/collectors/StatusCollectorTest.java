package edu.project3.collectors;

import edu.project3.logs.log_structure.LogResponse;
import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import java.util.List;
import edu.project3.statistic_collectors.StatusCollector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatusCollectorTest {
    @Test
    @DisplayName("testing collecting process")
    public void StatusCollectingProcessTest() {
        StatusCollector statusCollector = new StatusCollector();

        getLogsList().forEach(statusCollector::processLog);

        assertThat(statusCollector.getMetrics(3)).isEqualTo(getMetricsList());

        //required table row size is not equal to this metrics row size
        assertThrows(IllegalStateException.class, () -> statusCollector.getMetrics(10));
    }

    private static List<NginxLogRecord> getLogsList() {
        List<NginxLogRecord> logs = List.of(
            NginxLogRecord.builder()
                .response(LogResponse.builder()
                    .status(404)
                    .build())
                .build(),
            NginxLogRecord.builder()
                .response(LogResponse.builder()
                    .status(404)
                    .build())
                .build(),
            NginxLogRecord.builder()
                .response(LogResponse.builder()
                    .status(200)
                    .build())
                .build()
        );

        return logs;
    }

    private static List<MetricsRow> getMetricsList() {
        return List.of(
            new MetricsRow(List.of("404", "Not Found", "2")),
            new MetricsRow(List.of("200", "OK", "1"))
        );
    }
}
