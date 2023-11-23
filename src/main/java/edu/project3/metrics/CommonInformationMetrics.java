package edu.project3.metrics;

import edu.project3.statistic_collectors.AverageBytesSentSizeCollector;
import edu.project3.statistic_collectors.TimeController;
import java.time.OffsetDateTime;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonInformationMetrics extends MetricsContainer {
    private final String sources;
    private final static Logger LOGGER = LogManager.getLogger();

    public CommonInformationMetrics(OffsetDateTime from, OffsetDateTime to, String sources) {
        this.header = "Общая информация";
        collectors = List.of(new TimeController(from, to), new AverageBytesSentSizeCollector());
        setCols(2);
        this.sources = sources;
    }

    @Override
    public void build() {
        addRow(new MetricsRow(List.of("Метрика", "Значение")));
        addRow(
            MetricsRow.builder()
                .values(List.of("Файл(-ы)", sources))
                .build()
        );
        collectors.forEach(collector -> addRows(collector.getMetrics(cols)));

        LOGGER.info("Common Information table was built");
    }
}
