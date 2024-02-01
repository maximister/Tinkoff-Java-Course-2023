package edu.project3.collectors;

import edu.project3.logs.log_structure.LogRequest;
import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import edu.project3.statistic_collectors.MethodsCollector;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class MethodsCollectorTest {
    @Test
    @DisplayName("testing collecting process")
    public void MethodsCollectingProcessTest() {
        MethodsCollector methodsCollector = new MethodsCollector();

        getLogsList().forEach(methodsCollector::processLog);

        assertThat(methodsCollector.getMetrics(2)).isEqualTo(getMetricsList());

        //required table row size is not equal to this metrics row size
        assertThrows(IllegalStateException.class, () -> methodsCollector.getMetrics(1));
    }

    private static List<NginxLogRecord> getLogsList() {
        List<NginxLogRecord> logs = List.of(
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .method("GET")
                    .build())
                .build(),
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .method("GET")
                    .build())
                .build(),
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .method("HEAD")
                    .build())
                .build()
        );

        return logs;
    }

    private static List<MetricsRow> getMetricsList() {
        return List.of(
            new MetricsRow(List.of("HEAD", "1")),
            new MetricsRow(List.of("GET", "2"))
        );
    }
}
