package edu.project3.statistic_collectors;

import edu.project3.logs.log_structure.NginxLogRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*хз как лучше назвать класс, собирающий статистики
  Слово Collector вызывает у менея стойкие ассоциации с Collections и тд,
  но что-то лучше я пока не придумал*/
public abstract class StatisticsCollector {
    private StatisticsCollector nextCollector;
    protected String collectorsName;
    protected static final Logger LOGGER = LogManager.getLogger();

    //какие-то поля для сбора статы и формирования метрики
    public void setNextCollector(StatisticsCollector nextCollector) {
        this.nextCollector = nextCollector;
    }

    //сбор данных с одного лога
    public abstract void processLog(NginxLogRecord log);

    protected void processByNextCollector(NginxLogRecord log) {
        if (nextCollector != null) {
            nextCollector.processLog(log);
        }
    }
}
