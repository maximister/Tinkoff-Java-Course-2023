package edu.hw8.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import static edu.hw8.task2.countFibonacciForTest.count;
import static org.assertj.core.api.Assertions.assertThat;

public class MyFixedThreadPoolTest {
    @Test
    @DisplayName("testing fixed thread pool by calculating Fibonacci numbers")
    public void myFixedPoolTest_shouldCountFibonacciCorrectly() {
        ThreadPool threadPool = MyFixedThreadPool.newMyFixedThreadPool(8);
        List<Integer> expected = List.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55);

        threadPool.start();

        final List<Integer> actual = new CopyOnWriteArrayList<>();
        for (int i = 0; i <= 10; i++) {
            final int current = i;
            threadPool.execute(() -> actual.add(count(current)));
        }
        try {
            threadPool.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        threadPool.awaitTermination();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
