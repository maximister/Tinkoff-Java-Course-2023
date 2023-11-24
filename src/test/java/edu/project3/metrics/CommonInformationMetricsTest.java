package edu.project3.metrics;

import edu.project3.logs.log_structure.LogResponse;
import edu.project3.logs.log_structure.NginxLogRecord;
import java.time.OffsetDateTime;
import java.util.List;
import edu.project3.statistic_collectors.StatisticsCollector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CommonInformationMetricsTest {
    @Test
    @DisplayName("testing building tables")
    public void commonInformationMetrics_shouldBuildCorrectTable() {
        OffsetDateTime from = OffsetDateTime.MIN;
        OffsetDateTime to = OffsetDateTime.MAX;

        CommonInformationMetrics metrics = new CommonInformationMetrics(from, to, "source");

        //collecting information
        List<StatisticsCollector> collector = metrics.getCollectors();
        getLogsList().forEach(log -> collector.forEach(c -> c.processLog(log)));

        metrics.build();

        assertThat(metrics.getTable()).isEqualTo(getTable());
    }
    private static List<NginxLogRecord> getLogsList() {
        List<NginxLogRecord> logs = List.of(
            NginxLogRecord.builder()
                .localTime(OffsetDateTime.MIN)
                .response(LogResponse.builder()
                    .status(100)
                    .build())
                .build(),
            NginxLogRecord.builder()
                .localTime(OffsetDateTime.MIN)
                .response(LogResponse.builder()
                    .status(200)
                    .build())
                .build(),
            NginxLogRecord.builder()
                .localTime(OffsetDateTime.MIN)
                .response(LogResponse.builder()
                    .status(300)
                    .build())
                .build()
        );

        return logs;
    }

    private static List<MetricsRow> getTable() {
        return List.of(
            new MetricsRow(List.of("Метрика", "Значение")),
            new MetricsRow(List.of("Файл(-ы)", "source")),
            new MetricsRow(List.of("Начальная дата", " - ")),
            new MetricsRow(List.of("Конечная дата", " - ")),
            new MetricsRow(List.of("Количество запросов", "3")),
            new MetricsRow(List.of("Средний размер ответа", "0b"))
        );
    }
}
