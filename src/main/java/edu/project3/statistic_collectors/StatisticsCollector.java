package edu.project3.statistic_collectors;

import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import java.util.List;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*хз как лучше назвать класс, собирающий статистики
  Слово Collector вызывает у менея стойкие ассоциации с Collections и тд,
  но что-то лучше я пока не придумал*/
@Getter
public abstract class StatisticsCollector {
    private StatisticsCollector nextCollector;
    protected String collectorsName;
    protected final static String ROW_SIZE_ERROR = "Impossible to add this row to table";
    @Getter protected String statisticsName;
    protected static final Logger LOGGER = LogManager.getLogger();

    public void setNextCollector(StatisticsCollector nextCollector) {
        LOGGER.info(nextCollector.getCollectorsName() + " добавлен в цепь");
        this.nextCollector = nextCollector;
    }

    public abstract void processLog(NginxLogRecord log);

    protected void processByNextCollector(NginxLogRecord log) {
        if (nextCollector != null) {
            nextCollector.processLog(log);
        }
    }

    public abstract List<MetricsRow> getMetrics(int cols);
}
