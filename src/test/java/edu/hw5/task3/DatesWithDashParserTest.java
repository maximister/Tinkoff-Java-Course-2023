package edu.hw5.task3;

import edu.hw5.task3.date_parsers.AbstractDateParser;
import edu.hw5.task3.date_parsers.DatesWithDashParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DatesWithDashParserTest {
    @ParameterizedTest
    @MethodSource("parseDateTestArgs")
    @DisplayName("DatesWithDashParser test")
    public void parseDateTest(String date, Optional<LocalDate> result) {
        AbstractDateParser parser = new DatesWithDashParser();
        assertThat(parser.parseDate(date)).isEqualTo(result);
    }

    private static Stream<Arguments> parseDateTestArgs() {
        LocalDate date1 = LocalDate.of(1976, 3,21);
        LocalDate date2 = LocalDate.of(2020, 3,1);
        return Stream.of(
            Arguments.of("1976-3-21", Optional.of(date1)),
            Arguments.of("2020-3-1", Optional.of(date2)),
            Arguments.of("111-31-11212", Optional.empty()),
            Arguments.of("1-3-1976", Optional.empty())
        );
    }
}
