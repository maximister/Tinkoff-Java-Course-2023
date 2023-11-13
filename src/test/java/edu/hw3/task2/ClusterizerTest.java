package edu.hw3.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw3.task2.Clusterizer.clusterize;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClusterizerTest {
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "()()() | [(), (), ()]",
        "((())) | [((()))]",
        "((()))(())()()(()()) | [((())), (()), (), (), (()())]",
        "((())())(()(()())) | [((())()), (()(()()))]"
    })
    @DisplayName("Correct input data test")
    public void clusterizeWithCorrectDataTest(String input, String expected) {
        assertThat(clusterize(input).toString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("null test and empty string test")
    public void nullTest() {
        assertThrows(
            NullPointerException.class,
            () -> clusterize(null)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> clusterize("")
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
        "(({}))",
        "((test))()((()))",
        "((())",
        "(()))"
    })
    @DisplayName("Invalid input test")
    public void invalidInputTest(String input) {
        assertThrows(
            IllegalArgumentException.class,
            () -> clusterize(input)
        );
    }
}
