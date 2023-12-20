package edu.project3.metrics;

import edu.project3.logs.log_structure.LogRequest;
import edu.project3.logs.log_structure.NginxLogRecord;
import java.util.List;
import edu.project3.statistic_collectors.StatisticsCollector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HttpMethodsMetricsTest {
    @Test
    @DisplayName("testing building tables")
    public void httpMethodsMetrics_shouldBuildCorrectTable() {
        HttpMethodsMetrics metrics = new HttpMethodsMetrics();

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
                    .method("GET").build())
                .build(),
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .method("POST").build())
                .build(),
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .method("GET").build())
                .build()
        );

        return logs;
    }

    private static List<MetricsRow> getTable() {
        return List.of(
            new MetricsRow(List.of("Метод HTTP", "Количество")),
            new MetricsRow(List.of("POST", "1")),
            new MetricsRow(List.of("GET", "2"))
        );
    }
}
