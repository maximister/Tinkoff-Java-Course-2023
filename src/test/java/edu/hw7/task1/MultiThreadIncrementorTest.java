package edu.hw7.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

public class MultiThreadIncrementorTest {
    @ParameterizedTest
    @CsvSource(value = {
        "2, 100",
        "5, 10000",
        "1, 10000",
        "10, 100000",
        "30, 10000000",
        "1, 10000000"
    })
    @DisplayName("Testing multi thread incrementor")
    public void multiThreadIncrementorTest(int threadsAmount, int value) {
        MultiThreadIncrementor incrementor = new MultiThreadIncrementor(threadsAmount);
        incrementor.add(value);

        assertThat(incrementor.getCounter()).isEqualTo(value);
    }
}
