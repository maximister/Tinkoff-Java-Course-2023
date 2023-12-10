package edu.hw9.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;

public class StatsCollectorTest {
    @Test
    @DisplayName("Testing StatsCollector work")
    void statisticsCollectorTest() throws InterruptedException {
        StatsCollector collector = new StatsCollector();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(() -> collector.push("dataset1", new double[] {0.1, 0.2, 1.4, 5.1, 0.3}));
        executorService.execute(() -> collector.push("dataset2", new double[] {1, 2, 3, 4, 5}));
        executorService.shutdown();
        executorService.awaitTermination(10000, TimeUnit.NANOSECONDS);

        System.out.println(collector.stats());
        assertThat(collector.stats()).isEqualTo(new HashMap<>(Map.of(
            "dataset1", List.of(7.1, 1.42, 5.1, 0.1),
            "dataset2", List.of(15.0, 3.0, 5.0, 1.0)
        )));
    }
}
