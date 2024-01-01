package edu.hw10.task2;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CacheProxyTest {
    @SneakyThrows
    @Test
    @DisplayName("testing cacheProxy with fibCalculator")
    void cacheProxy_shouldCacheFibonacciNumbers() {
        Path dirPath = Path.of("src/main/java/edu/hw10/task2/dir_for_cache/");
        Path testFilePath = Path.of(dirPath.toString(), "fib.txt");
        if (Files.exists(testFilePath)) {
            Files.delete(testFilePath);
        }

        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        FibCalculator fibCalculator = new MyFibCalculator();
        FibCalculator proxy = CacheProxy.create(fibCalculator, FibCalculator.class);

        long noCacheStart = System.nanoTime();
        long noCacheResult = proxy.fib(20);
        long noCacheEnd = System.nanoTime();

        long cacheStart = System.nanoTime();
        long cacheResult = proxy.fib(20);
        long cacheEnd = System.nanoTime();

        assertAll(
            () -> assertThat(noCacheEnd - noCacheStart).isGreaterThan(cacheEnd - cacheStart),
            () -> assertThat(noCacheResult).isEqualTo(cacheResult)
        );

        Files.delete(testFilePath);
    }
}
