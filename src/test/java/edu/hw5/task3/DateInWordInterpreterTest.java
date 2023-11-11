package edu.hw5.task3;

import edu.hw5.task3.date_parsers.AbstractDateParser;
import edu.hw5.task3.date_parsers.DateInWordInterpreter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DateInWordInterpreterTest {
    @ParameterizedTest
    @MethodSource("parseDateTestArgs")
    @DisplayName("DateInWordInterpreter test")
    public void parseDateTest(String date, Optional<LocalDate> result) {
        AbstractDateParser parser = new DateInWordInterpreter();
        assertThat(parser.parseDate(date)).isEqualTo(result);
    }


    private static Stream<Arguments> parseDateTestArgs() {
        return Stream.of(
            Arguments.of("today", Optional.of(LocalDate.now())),
            Arguments.of("tomorrow", Optional.of(LocalDate.now().plusDays(1))),
            Arguments.of("yesterday", Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("day before yesterday", Optional.of(LocalDate.now().minusDays(2))),
            Arguments.of("Day before yesterday", Optional.of(LocalDate.now().minusDays(2))),
            Arguments.of("The day before yesterday", Optional.of(LocalDate.now().minusDays(2))),
            Arguments.of("The Day before yesterday", Optional.of(LocalDate.now().minusDays(2))),
            Arguments.of("day after tomorrow", Optional.of(LocalDate.now().plusDays(2))),
            Arguments.of("Day after tomorrow", Optional.of(LocalDate.now().plusDays(2))),
            Arguments.of("The Day after tomorrow", Optional.of(LocalDate.now().plusDays(2))),
            Arguments.of("the day after tomorrow", Optional.of(LocalDate.now().plusDays(2))),
            Arguments.of("123 days ago", Optional.of(LocalDate.now().minusDays(123))),
            Arguments.of("0 days ago", Optional.of(LocalDate.now().minusDays(0))),
            Arguments.of("123 day ago", Optional.of(LocalDate.now().minusDays(123))),
            Arguments.of("123 day after", Optional.of(LocalDate.now().plusDays(123))),
            Arguments.of("123 day next", Optional.of(LocalDate.now().plusDays(123))),
            Arguments.of("123 days lately", Optional.of(LocalDate.now().plusDays(123))),
            Arguments.of("todays", Optional.empty()),
            Arguments.of("tomorow", Optional.empty()),
            Arguments.of("yersterday", Optional.empty()),
            Arguments.of("a week ago", Optional.empty()),
            Arguments.of("1/2/2020", Optional.empty()),
            Arguments.of("IvanZolo2004", Optional.empty())
        );
    }
}
