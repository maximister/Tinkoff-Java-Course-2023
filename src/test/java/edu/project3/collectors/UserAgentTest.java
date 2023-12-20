package edu.project3.collectors;

import edu.project3.logs.log_structure.LogRequest;
import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import java.util.List;
import edu.project3.statistic_collectors.UserAgentCollector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserAgentTest {
    @Test
    @DisplayName("testing collecting process")
    public void UserAgentProcessingTest() {
        UserAgentCollector userAgentCollector = new UserAgentCollector();

        getLogsList().forEach(userAgentCollector::processLog);

        assertThat(userAgentCollector.getMetrics(2)).isEqualTo(getMetricsList());

        //required table row size is not equal to this metrics row size
        assertThrows(IllegalStateException.class, () -> userAgentCollector.getMetrics(10));
    }

    private static List<NginxLogRecord> getLogsList() {
        List<NginxLogRecord> logs = List.of(
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .agent("IvanZolo2004").build())
                .build(),
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .agent("IvanZolo2004").build())
                .build(),
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .agent("Doogin").build())
                .build(),
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .agent("LoveWinter").build())
                .build()
        );

        return logs;
    }

    private static List<MetricsRow> getMetricsList() {
        return List.of(
            new MetricsRow(List.of("IvanZolo2004", "2")),
            new MetricsRow(List.of("Doogin", "1")),
            new MetricsRow(List.of("LoveWinter", "1"))
        );
    }
}
