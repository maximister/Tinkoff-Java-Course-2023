package edu.project3.statistic_collectors;

import edu.project3.logs.log_structure.LogResponse;
import edu.project3.logs.log_structure.NginxLogRecord;
import java.math.BigInteger;

public class AverageBytesSentSizeCollector extends StatisticsCollector {
    //TODO: количество логов по идее оптимальнее не считать тут, а взять в конце как размер листа логов
    // но при замене листа на потоки хз что выйдет надо думатб
    // поробовать разработать какую-то формулу
    private long amount;
    //не уверен, что это норм вариант, но пока так
    private BigInteger allBytesSent;

    public AverageBytesSentSizeCollector() {
        collectorsName = "AvgBytesSentSizeCollector";
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

    public long getAverageBytesSentSize() {
        return allBytesSent.divide(BigInteger.valueOf(amount)).longValueExact();
    }
}
