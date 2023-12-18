package edu.project5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FastReflectionTest {
    @Test
    @DisplayName("running benchmark")
    public void fastReflectionTest() {
        ReflectionBenchmark benchmark = new ReflectionBenchmark();
        benchmark.run();
    }
}
