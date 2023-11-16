package edu.hw5.task7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw5.task7.BinaryStringValidator.doesContainsAtLeastThreeSymbolsAndThirdIsZero;
import static edu.hw5.task7.BinaryStringValidator.isFirstSymbolEqualsToLast;
import static edu.hw5.task7.BinaryStringValidator.isLengthMoreThanOneAndLessThanThree;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BinaryStringValidatorTest {
    @ParameterizedTest
    @CsvSource(value = {
        "100101, true",
        "0100, true",
        "100, true",
        "000, true",
        "120, false",
        "10, false",
        "011, false",
        "IvanZolo2004, false"
    })
    @DisplayName("task1 test")
    public void doesContainsAtLeastThreeSymbolsAndThirdIsZeroTest(String value, boolean expected) {
        assertThat(doesContainsAtLeastThreeSymbolsAndThirdIsZero(value)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "10100001, true",
        "01000010, true",
        "1, true",
        "0, true",
        "121, false",
        "1001010, false",
        "010101101, false",
        "IvanZolo2004, false"
    })
    @DisplayName("task2 test")
    public void isFirstSymbolEqualsToLastTest(String value, boolean expected) {
        assertThat(isFirstSymbolEqualsToLast(value)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "101, true",
        "01, true",
        "1, true",
        "0, true",
        "121, false",
        "1001010, false",
        "IvanZolo2004, false"
    })
    @DisplayName("task3 test")
    public void isLengthMoreThanOneAndLessThanThreeTest(String value, boolean expected) {
        assertThat(isLengthMoreThanOneAndLessThanThree(value)).isEqualTo(expected);
    }
}
