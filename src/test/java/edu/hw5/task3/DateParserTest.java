package edu.hw5.task3;

import edu.hw5.task3.date_parsers.AbstractDateParser;
import edu.hw5.task3.date_parsers.DateInWordInterpreter;
import edu.hw5.task3.date_parsers.DatesWithDashParser;
import edu.hw5.task3.date_parsers.DatesWithSlashParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DateParserTest {
    public List<AbstractDateParser> getParsersList() {
        return List.of(
            new DateInWordInterpreter(), new DatesWithDashParser(), new DatesWithSlashParser());
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParser")
    @DisplayName("DateParser test")
    public void dateParserTest(String date, Optional<LocalDate> result) {
        DateParser parser = new DateParser();
        parser.setDateParsers(getParsersList());
        assertThat(parser.parseDate(date)).isEqualTo(result);
    }

    private static Stream<Arguments> getArgumentsForParser() {
        return Stream.of(
            Arguments.of("today", Optional.of(LocalDate.now())),
            Arguments.of("tomorrow", Optional.of(LocalDate.now().plusDays(1))),
            Arguments.of("yesterday", Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("day before yesterday", Optional.of(LocalDate.now().minusDays(2))),
            Arguments.of("The day before yesterday", Optional.of(LocalDate.now().minusDays(2))),
            Arguments.of("day after tomorrow", Optional.of(LocalDate.now().plusDays(2))),
            Arguments.of("the day after tomorrow", Optional.of(LocalDate.now().plusDays(2))),
            Arguments.of("123 days ago", Optional.of(LocalDate.now().minusDays(123))),
            Arguments.of("123 day after", Optional.of(LocalDate.now().plusDays(123))),
            Arguments.of("123 day next", Optional.of(LocalDate.now().plusDays(123))),
            Arguments.of("123 days lately", Optional.of(LocalDate.now().plusDays(123))),
            Arguments.of("1976-3-21", Optional.of(LocalDate.of(1976, 3,21))),
            Arguments.of("2020-3-1", Optional.of(LocalDate.of(2020, 3,1))),
            Arguments.of("1/3/1976", Optional.of(LocalDate.of(1976, 3,1))),
            Arguments.of("1/3/20", Optional.of(LocalDate.of(2020, 3,1))),
            Arguments.of("vchera", Optional.empty()),
            Arguments.of("IvanZolo2004", Optional.empty())
            );
    }

    @Test
    @DisplayName("DateParser test with no parsers")
    public void dateParserTestWithNoParsers() {
        DateParser parser = new DateParser();
        assertThat(parser.parseDate("today")).isEqualTo(Optional.empty());
    }

    @ParameterizedTest
    @MethodSource("getArgumentsForParser")
    @DisplayName("DateParser test")
    public void addParserTest(String date, Optional<LocalDate> result) {
        DateParser parser = new DateParser();
        parser.addDateParser(new DatesWithSlashParser());
        parser.addDateParser(new DatesWithDashParser());
        parser.addDateParser(new DateInWordInterpreter());
        assertThat(parser.parseDate(date)).isEqualTo(result);
    }
}
