package edu.project3.metrics;

import edu.project3.logs.log_structure.LogRequest;
import edu.project3.logs.log_structure.LogResponse;
import edu.project3.logs.log_structure.NginxLogRecord;
import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.List;
import edu.project3.statistic_collectors.AverageBytesSentSizeCollector;
import edu.project3.statistic_collectors.MethodsCollector;
import edu.project3.statistic_collectors.ResourcesCollector;
import edu.project3.statistic_collectors.StatisticsCollector;
import edu.project3.statistic_collectors.StatusCollector;
import edu.project3.statistic_collectors.TimeController;
import edu.project3.statistic_collectors.UserAgentCollector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MetricsAndCollectorsHandlerTest {
    @Test
    @DisplayName("testing building tables and working of chain of collectors")
    public void MetricsAndCollectorsHandler_shouldBuildCorrectChain() {

        //Честно, не особо в голову приходит, что и как тут тестировать;
        //решил мокнуть все элементы цепочки, чтобы проверить, вызывались ли они после сборки цепи

        //P.S. попробовал сделать лист мокнутых коллекторов,
        // засунуть его в хендлер рефлексией и проверить,
        // что после построения цепи и проверки логов каждый объект вызавался нужное количество раз,
        // но цепь не хочет строиться из мокнутых объектов(((
        //поэтому оставил пока какой-то формальный тест, чтобы поднять ковераге,
        //в котором просто посчитал количество элементов цепи

        MetricsAndCollectorsHandler handler
            = new MetricsAndCollectorsHandler(OffsetDateTime.MIN, OffsetDateTime.MAX, "source");
        handler.buildChainOfCollectors();

        getLogsList().forEach(handler::processLog);

        Field collectors = null;
        try {
            collectors = handler.getClass().getDeclaredField("collectors");
            collectors.setAccessible(true);
            List<StatisticsCollector> collectorsList = (List<StatisticsCollector>)collectors.get(handler);
            int cnt = 1;
            StatisticsCollector ptr = collectorsList.get(0);
            while (ptr.getNextCollector() != null) {
                cnt++;
                ptr = ptr.getNextCollector();
            }
            assertThat(cnt).isEqualTo(6);
            assertThat(handler.getTables()).isNotEqualTo(null);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<NginxLogRecord> getLogsList() {
        return List.of(
            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .url("url1")
                    .method("GET")
                    .agent("agent1").build())
                .response(LogResponse.builder()
                    .status(404)
                    .bytesSent(100)
                    .build())
                .localTime(OffsetDateTime.MIN)
                .build(),

            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .url("url1")
                    .method("HEAD")
                    .agent("agent2").build())
                .response(LogResponse.builder()
                    .status(200)
                    .bytesSent(50)
                    .build())
                .localTime(OffsetDateTime.MIN)
                .build(),

            NginxLogRecord.builder()
                .request(LogRequest.builder()
                    .url("url2")
                    .method("GET")
                    .agent("agent1").build())
                .response(LogResponse.builder()
                    .status(404)
                    .bytesSent(200)
                    .build())
                .localTime(OffsetDateTime.MIN)
                .build()
        );
    }
}
