package edu.project3.statistic_collectors;

import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import java.time.OffsetDateTime;
import java.util.List;

//служит как бы фильтром, не допускающим логи с
// неподходящим временем к дальнейшей обрботке
public class TimeController extends StatisticsCollector {
    private final OffsetDateTime from;
    private final OffsetDateTime to;
    private final static String NO_DATE_SYMBOL = " - ";

    public TimeController(OffsetDateTime from, OffsetDateTime to) {
        collectorsName = "TimeController";
        LOGGER.info(collectorsName + " was created");

        this.from = from;
        this.to = to;
    }

    @Override public void processLog(NginxLogRecord log) {
        if ((log.localTime().isAfter(from) || log.localTime().isEqual(from))
            && (log.localTime().isBefore(to) || log.localTime().isEqual(to))) {
            processByNextCollector(log);
        }
    }

    @Override public List<MetricsRow> getMetrics(int cols) {
        if (cols != 2) {
            throw new IllegalStateException(ROW_SIZE_ERROR);
        }

        String startDate = from.isEqual(OffsetDateTime.MIN) ? from.toLocalDate().toString() : NO_DATE_SYMBOL;
        String endDate = to.isEqual(OffsetDateTime.MAX) ? to.toLocalDate().toString() : NO_DATE_SYMBOL;

        return List.of(
            MetricsRow.builder()
                .name("Начальная дата")
                .values(List.of(startDate))
                .build(),
            MetricsRow.builder()
                .name("Конечная дата")
                .values(List.of(endDate))
                .build()
        );
    }
}
