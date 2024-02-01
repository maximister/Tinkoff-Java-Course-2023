package edu.project3.collectors;

import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import edu.project3.statistic_collectors.TimeController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeControllerTest {
    @ParameterizedTest
    @MethodSource("dateAndMetricsSource")
    @DisplayName("test with different from and to dates")
    public void TimeController_ShouldReturnCorrectMetrics(
        List<OffsetDateTime> dates,
        List<MetricsRow> metrics,
        int correctLogs
    ) {
        TimeController timeController = new TimeController(dates.get(0), dates.get(1));
        CollectorForTestingFilters collector = new CollectorForTestingFilters();
        timeController.setNextCollector(collector);

        getLogsList().forEach(timeController::processLog);

        assertThat(timeController.getMetrics(2)).isEqualTo(metrics);
        assertThat(collector.getCounter()).isEqualTo(correctLogs);

        //required table row size is not equal to this metrics row size
        assertThrows(IllegalStateException.class, () -> timeController.getMetrics(10));
    }

    private static List<NginxLogRecord> getLogsList() {
        DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("dd/LLL/yyyy:HH:mm:ss ZZ", Locale.US);

        List<NginxLogRecord> logs = List.of(
            NginxLogRecord.builder()
                .localTime(OffsetDateTime.parse("17/May/2015:08:05:12 +0000", formatter))
                .build(),
            NginxLogRecord.builder()
                .localTime(OffsetDateTime.parse("17/Jun/2018:08:05:12 +0000", formatter))
                .build(),
            NginxLogRecord.builder()
                .localTime(OffsetDateTime.parse("17/May/2020:08:05:12 +0000", formatter))
                .build()
        );

        return logs;
    }

    private static List<MetricsRow> getMetricsList(List<String> values) {
        return List.of(
            new MetricsRow(List.of("Начальная дата", values.get(0))),
            new MetricsRow(List.of("Конечная дата", values.get(1)))
        );
    }

    private static Stream<Arguments> dateAndMetricsSource() {
        DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);

        List<OffsetDateTime> dates1 = List.of(
            OffsetDateTime.of(
                LocalDate.parse("2014-01-01", formatter),
                LocalTime.MIN, ZoneOffset.UTC
            ),
            OffsetDateTime.of(
                LocalDate.parse("2021-01-01", formatter),
                LocalTime.MAX, ZoneOffset.UTC
            )
        );
        List<OffsetDateTime> dates2 = List.of(
            OffsetDateTime.MIN,
            OffsetDateTime.MAX
        );
        List<OffsetDateTime> dates3 = List.of(
            OffsetDateTime.of(
                LocalDate.parse("2018-01-01", formatter),
                LocalTime.MIN, ZoneOffset.UTC
            ),
            OffsetDateTime.of(
                LocalDate.parse("2019-01-01", formatter),
                LocalTime.MAX, ZoneOffset.UTC
            )
        );

        return Stream.of(
            Arguments.of(dates1, getMetricsList(List.of("2014-01-01", "2021-01-01")), 3),
            Arguments.of(dates2, getMetricsList(List.of(" - ", " - ")), 3),
            Arguments.of(dates3, getMetricsList(List.of("2018-01-01", "2019-01-01")), 1)
        );
    }
}
