package edu.hw5.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw5.task1.ComputerClubAnalyzer.getAverageDurationOfSession;
import static edu.hw5.task1.ComputerClubAnalyzer.getAverageSessionDurationForPrint;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComputerClubAnalyzerTest {
    @ParameterizedTest
    @MethodSource("correctInputTestArgs")
    @DisplayName("correct sessions test")
    public void getAverageDurationCorrectInputTest(List<String> sessions, long expected) {
        assertThat(getAverageDurationOfSession(sessions).toMinutes()).isEqualTo(expected);
    }

    private static Stream<Arguments> correctInputTestArgs() {
        return Stream.of(
            Arguments.of(
                List.of("2022-03-12, 20:20 - 2022-03-12, 23:50", "2022-04-01, 21:30 - 2022-04-02, 01:20"),
                220),
            Arguments.of(
                List.of("2020-01-01, 00:00 - 2020-01-01, 16:00",
                    "2022-04-01, 21:30 - 2022-04-02, 01:20", "2020-01-01, 00:00 - 2020-01-01, 07:10",
                    "2000-05-30, 05:00 - 2000-05-31, 15:40"),
                925)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidInputTestArgs")
    @DisplayName("invalid sessions test")
    public void getAverageDurationInvalidInputTest(List<String> sessions) {
        assertThrows(IllegalArgumentException.class,
            () -> getAverageDurationOfSession(sessions));
    }

    private static Stream<Arguments> invalidInputTestArgs() {
        return Stream.of(
            Arguments.of(List.of("2020-01-02, 00:00 - 2020-01-01, 16:00")),
            Arguments.of(List.of("2020:01:02, 00:00 - 2020-01-01, 16:00")),
            Arguments.of(List.of("2020-01-02, 00:00  2020-01-01, 16:00"))
        );
    }

    @Test
    @DisplayName("formatted output test")
    public void getAverageSessionDurationForPrintTest() {
        List<String> testList =
            List.of("2022-03-12, 20:20 - 2022-03-12, 23:50", "2022-04-01, 21:30 - 2022-04-02, 01:20");
        String expected = "average duration is: 3h 40m";

        assertThat(getAverageSessionDurationForPrint(testList)).isEqualTo(expected);
    }
}
