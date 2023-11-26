package edu.hw7.task2;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import static edu.hw7.task2.ParallelFactorialCounter.getFactorial;
import static org.assertj.core.api.Assertions.assertThat;

public class ParallelFactorialCounterTest {
    @ParameterizedTest
    @CsvSource(value = {
        "0, 1",
        "1, 1",
        "5, 120"
    })
    @DisplayName("testing parallel factorial")
    public void parallelFactorialCounterTest(long value, String stringExpected) {
        BigInteger expected = new BigInteger(stringExpected);

        assertThat(getFactorial(value)).isEqualTo(expected);
    }

    @Test
    @DisplayName("testing parallel factorial with big value")
    public void parallelFactorialCounterTest() {
        try (InputStream fis = new FileInputStream("src/test/java/edu/hw7/task2/longFactorial.txt")) {
            String longString = IOUtils.toString(fis, StandardCharsets.UTF_8);
            BigInteger expected = new BigInteger(longString.trim());
            assertThat(getFactorial(100000)).isEqualTo(expected);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
