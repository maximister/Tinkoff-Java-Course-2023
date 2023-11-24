package edu.project3.metrics;

import edu.project3.logs.log_structure.LogResponse;
import edu.project3.logs.log_structure.NginxLogRecord;
import java.util.List;
import edu.project3.statistic_collectors.StatisticsCollector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ResponseStatusMetricsTest {
    @Test
    @DisplayName("testing building tables")
    public void ResponseStatusMetrics_shouldBuildCorrectTable() {
        ResponseStatusMetrics metrics = new ResponseStatusMetrics();

        //collecting information
        StatisticsCollector collector = metrics.getCollectors().get(0);
        getLogsList().forEach(collector::processLog);

        metrics.build();

        assertThat(metrics.getTable()).isEqualTo(getTable());
    }
    private static List<NginxLogRecord> getLogsList() {
        List<NginxLogRecord> logs = List.of(
            NginxLogRecord.builder()
                .response(LogResponse.builder()
                    .status(404).build())
                .build(),
            NginxLogRecord.builder()
                .response(LogResponse.builder()
                    .status(200).build())
                .build(),
            NginxLogRecord.builder()
                .response(LogResponse.builder()
                    .status(404).build())
                .build()
        );

        return logs;
    }

    private static List<MetricsRow> getTable() {
        return List.of(
            new MetricsRow(List.of("Код", "Имя", "Количество")),
            new MetricsRow(List.of("404", "Not Found", "2")),
            new MetricsRow(List.of("200", "OK", "1"))
        );
    }
}
