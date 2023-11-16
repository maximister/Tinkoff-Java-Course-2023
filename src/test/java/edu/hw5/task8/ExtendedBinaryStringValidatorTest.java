package edu.hw5.task8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw5.task8.ExtendedBinaryStringValidator.isEveryOddSymbolIsOne;
import static edu.hw5.task8.ExtendedBinaryStringValidator.isLengthOdd;
import static edu.hw5.task8.ExtendedBinaryStringValidator.isMoreThan2ZerosAnd1OrNoOnes;
import static edu.hw5.task8.ExtendedBinaryStringValidator.isNoSequentialOnes;
import static edu.hw5.task8.ExtendedBinaryStringValidator.isNotTwoOrTreeOnes;
import static edu.hw5.task8.ExtendedBinaryStringValidator.isZeroAndOddLenOrOneAndEvenLen;
import static edu.hw5.task8.ExtendedBinaryStringValidator.isZerosMultipleTree;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//Тут, кстати, тесты помогли найти много ошибок
public class ExtendedBinaryStringValidatorTest {
    @ParameterizedTest
    @CsvSource(value = {
        "1001010, true",
        "010, true",
        "111, true",
        "000, true",
        "120, false",
        "10, false",
        "0111, false",
        "010000, false"
    })
    @DisplayName("task 1 test")
    public void isLengthOddTest(String value, boolean expected) {
        assertThat(isLengthOdd(value)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "010101101, true",
        "10101101, true",
        "01, false",
        "111, false"
    })
    @DisplayName("task 2 test")
    public void isZeroAndOddLenOrOneAndEvenLenTest(String value, boolean expected) {
        assertThat(isZeroAndOddLenOrOneAndEvenLen(value)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "1111, true",
        "10010, true",
        "0111000100, true",
        "1110, false",
        "0, false",
        "00111111, false"
    })
    @DisplayName("task 3 test")
    public void isZerosMultipleTreeTest(String value, boolean expected) {
        assertThat(isZerosMultipleTree(value)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "1111, true",
        "10010, true",
        "0111000100, true",
        "1110, true",
        "0, true",
        "11111111, true",
        "110, true",
        "0111, true",
        "11, false",
        "111, false"
    })
    @DisplayName("task 4 test")
    public void isNotTwoOrTreeOnesTest(String value, boolean expected) {
        assertThat(isNotTwoOrTreeOnes(value)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "1111, true",
        "1010, true",
        "1110, true",
        "10, true",
        "0, false",
        "01, false",
        "11011, false"
    })
    @DisplayName("task 5 test")
    public void isEveryOddSymbolIsOneTest(String value, boolean expected) {
        assertThat(isEveryOddSymbolIsOne(value)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "100, true",
        "000, true",
        "000100, true",
        "110, false",
        "0, false",
        "10000000010, false",
        "01, false",
        "11011, false"
    })
    @DisplayName("task 6 test")
    public void isMoreThan2ZerosAnd1OrNoOnesTest(String value, boolean expected) {
        assertThat(isMoreThan2ZerosAnd1OrNoOnes(value)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "000, true",
        "100, true",
        "1, true",
        "0001, true",
        "00100, true",
        "010, true",
        "0010, true",
        "0001001, true",
        "10010, true",
        "01010, true",
        "010101, true",
        "00010001, true",
        "00000001000100, true",
        "10101010000110, false",
        "11111111111111, false",
        "11, false"
    })
    @DisplayName("task 7 test")
    public void isNoSequentialOnesTest(String value, boolean expected) {
        assertThat(isNoSequentialOnes(value)).isEqualTo(expected);
    }
}
