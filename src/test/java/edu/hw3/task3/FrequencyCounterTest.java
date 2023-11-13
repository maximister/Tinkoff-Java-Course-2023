package edu.hw3.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw3.task3.FrequencyCounter.getFrequencyDict;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FrequencyCounterTest {
    @ParameterizedTest
    @MethodSource("getFrequencyDictTestArgs")
    @DisplayName("Тестирование на корректных данных разных типов")
    public <T> void getFrequencyDictTest(List<T> testList, String expectedDict) {

        assertThat(getFrequencyDict(testList).toString().replace("=", ": ")).isEqualTo(expectedDict);
    }

    private static Stream<Arguments> getFrequencyDictTestArgs() {
        return Stream.of(
            Arguments.of(Arrays.asList("a", "bb", "a", "bb"), "{bb: 2, a: 2}"),
            Arguments.of(Arrays.asList("this", "and", "that", "and"), "{that: 1, and: 2, this: 1}"),
            Arguments.of(Arrays.asList("код", "код", "код", "bug"), "{код: 3, bug: 1}"),
            Arguments.of(Arrays.asList(1, 1, 2, 2), "{1: 2, 2: 2}")
        );
    }

    @Test
    @DisplayName("null test")
    public void nullTest() {
        List<Integer> nullList = null;

        assertThrows(
            NullPointerException.class,
            () -> getFrequencyDict(nullList)
        );
    }

    @Test
    @DisplayName("empty list test")
    public void emptyListTest() {
        List<Integer> emptyList = new ArrayList<>();
        assertThrows(
            IllegalArgumentException.class,
            () -> getFrequencyDict(emptyList)
        );
    }
}
