package edu.hw10.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CacheProxyTest {
    @Test
    @DisplayName("testing cacheProxy with fibCalculator")
    void cacheProxy_shouldCacheFibonacciNumbers() {
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
    }
}
