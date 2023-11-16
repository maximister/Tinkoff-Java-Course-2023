package edu.hw5.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.stream.Stream;
import static edu.hw5.task2.Friday13Seeker.findAllFriday13;
import static edu.hw5.task2.Friday13Seeker.getNextFriday13;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Friday13SeekerTest {
    @ParameterizedTest
    @MethodSource("findAllFriday13TestArgs")
    @DisplayName("findAllFriday13 test with correct years")
    public void findAllFriday13Test(int year, String expected) {
        assertThat(findAllFriday13(year).toString()).isEqualTo(expected);
    }

    private static Stream<Arguments> findAllFriday13TestArgs() {
        return Stream.of(
            Arguments.of(1925, "[1925-02-13, 1925-03-13, 1925-11-13]"),
            Arguments.of(2024, "[2024-09-13, 2024-12-13]"),
            Arguments.of(0, "[0000-10-13]")
        );
    }

    @Test
    @DisplayName("findAllFriday13 test with negative year")
    public void findAllFriday13WithNegativeYearTest() {
        assertThrows (IllegalArgumentException.class,
            () -> findAllFriday13(-1));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "2024-08-10, 2024-09-13",
        "1925-02-14, 1925-03-13"
    })
    @DisplayName("getNextFriday13 test with correct dates")
    public void getNextFriday13Test(String date, String expected) {
        LocalDate testDate = LocalDate.parse(date);
        LocalDate expectedDate = LocalDate.parse(expected);

        assertThat(getNextFriday13(testDate)).isEqualTo(expectedDate);
    }

    //Это лишнее наверное, да?
    @Test
    @DisplayName("getNextFriday13 test with null")
    public void getNextFriday13Test() {

        assertThrows(NullPointerException.class,
            () -> getNextFriday13(null));
    }
}
