package edu.hw3.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw3.task4.RomanConverter.convertToRoman;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RomanConverterTest {
    @ParameterizedTest
    @CsvSource(value = {
        "2, II",
        "12, XII",
        "16, XVI",
        "3999,MMMCMXCIX"
    })
    @DisplayName("Correct input test")
    public void converterTest(int value, String expected) {
        assertThat(convertToRoman(value)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "4000",
        "0",
        "-12"
    })
    @DisplayName("Invalid input test")
    public void converterWithInvalidInputTest(int value) {
        assertThrows(
            IllegalArgumentException.class,
            () -> convertToRoman(value)
        );
    }
}
