package edu.project3.collectors;

import edu.project3.logs.log_structure.LogResponse;
import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import edu.project3.statistic_collectors.AverageBytesSentSizeCollector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AverageBytesSentCollectorTest {
    @Test
    @DisplayName("testing collecting process")
    public void AvgBytesSentCollectorTest() {
        AverageBytesSentSizeCollector averageBytesSentCollector = new AverageBytesSentSizeCollector();

        getLogsList().forEach(averageBytesSentCollector::processLog);

        try {
            Field amountField = averageBytesSentCollector
                .getClass()
                .getDeclaredField("amount");
            amountField.setAccessible(true);
            long amount = (long) amountField.get(averageBytesSentCollector);

            Field allBytesField = averageBytesSentCollector
                .getClass()
                .getDeclaredField("allBytesSent");
            allBytesField.setAccessible(true);
            BigInteger allBytesSent = (BigInteger) allBytesField.get(averageBytesSentCollector);

            assertThat(amount).isEqualTo(4);
            assertThat(allBytesSent).isEqualTo(new BigInteger("29"));
            assertThat(averageBytesSentCollector.getMetrics(2)).isEqualTo(getMetricsList());

            //required table row size is not equal to this metrics row size
            assertThrows(IllegalStateException.class,
                () -> averageBytesSentCollector.getMetrics(12));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<NginxLogRecord> getLogsList() {
        List<NginxLogRecord> logs = List.of(
            NginxLogRecord.builder()
                .response(LogResponse.builder()
                    .bytesSent(12).build())
                .build(),
            NginxLogRecord.builder()
                .response(LogResponse.builder()
                    .bytesSent(16).build())
                .build(),
            NginxLogRecord.builder()
                .response(LogResponse.builder()
                    .bytesSent(1).build())
                .build(),
            NginxLogRecord.builder()
                .response(LogResponse.builder()
                    .bytesSent(0).build())
                .build()
        );

        return logs;
    }

    private static List<MetricsRow> getMetricsList() {
        MetricsRow requestAmount
            = new MetricsRow(List.of("Количество запросов", "4"));
        MetricsRow avgBytesSent
            = new MetricsRow(List.of("Средний размер ответа",
            Long.toString(7) + "b")
        );

        return List.of(requestAmount, avgBytesSent);
    }
}
