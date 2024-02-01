package edu.project3.collectors;

import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import edu.project3.statistic_collectors.StatisticsCollector;
import lombok.Getter;
import java.util.List;

//Класс для теста фильтра по времени - считает, сколько логов прошло через фильтр
@Getter
public class CollectorForTestingFilters extends StatisticsCollector {
    private int counter;

    public CollectorForTestingFilters() {
        counter = 0;
    }

    @Override
    public void processLog(NginxLogRecord log) {
        counter++;
    }

    @Override
    public List<MetricsRow> getMetrics(int cols) {
        return null;
    }
}
