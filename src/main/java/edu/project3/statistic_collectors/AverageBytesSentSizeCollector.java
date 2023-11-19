package edu.project3.statistic_collectors;

import edu.project3.logs.log_structure.LogResponse;
import edu.project3.logs.log_structure.NginxLogRecord;
import edu.project3.metrics.MetricsRow;
import java.math.BigInteger;
import java.util.List;

public class AverageBytesSentSizeCollector extends StatisticsCollector {
    //TODO: количество логов по идее оптимальнее не считать тут, а взять в конце как размер листа логов
    // но при замене листа на потоки хз что выйдет надо думатб
    // поробовать разработать какую-то формулу
    private long amount;
    //не уверен, что это норм вариант, но пока так
    private BigInteger allBytesSent;

    public AverageBytesSentSizeCollector() {
        collectorsName = "AvgBytesSentSizeCollector";
        LOGGER.info(collectorsName + " was created");
        amount = 0;
        allBytesSent = new BigInteger("0");
    }

    @Override
    public void processLog(NginxLogRecord log) {
        LogResponse response = log.response();
        allBytesSent = allBytesSent.add(BigInteger.valueOf(response.bytesSent()));
        amount++;

        processByNextCollector(log);
    }

    @Override
    public List<MetricsRow> getMetrics(int cols) {
        MetricsRow requestAmount
            = new MetricsRow("Количество запросов", List.of(Long.toString(amount)));
        MetricsRow avgBytesSent
            = new MetricsRow(
            "Средний размер ответа",
            List.of(Long.toString(getAverageBytesSentSize()) + "b")
        );

        if (!requestAmount.isValid(cols) || !avgBytesSent.isValid(cols)) {
            throw new IllegalStateException(ROW_SIZE_ERROR);
        }

        return List.of(requestAmount, avgBytesSent);
    }

    //TODO:наверное удалить
    private long getAverageBytesSentSize() {
        return allBytesSent.divide(BigInteger.valueOf(amount)).longValueExact();
    }

    private long getRequestAmount() {
        return amount;
    }
}
