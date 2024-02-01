package edu.project3.parsers;

import edu.project3.input_arguments_parser.ArgumentsParser;
import edu.project3.input_arguments_parser.ParsedInput;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArgumentsParserTest {
    @ParameterizedTest
    @MethodSource("getCorrectArguments")
    @DisplayName("Parsing correct arguments")
    public void parseInput_ShouldReturnCorrectlyParsedArguments(String value, ParsedInput expected) {
        ArgumentsParser parser = new ArgumentsParser(value);
        assertThat(parser.getParsedInput()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getEmptyInputArguments")
    @DisplayName("test with invalid input (null/empty/blank)")
    public void parseInput_whenGettingEmptyInput_shouldThrowIllegalArgumentException(String value) {
        assertThrows(IllegalArgumentException.class,
            () -> new ArgumentsParser(value)
        );
    }

    @ParameterizedTest
    @MethodSource("getInvalidInputArguments")
    @DisplayName("test with invalid input (no source or format)")
    public void parseInput_whenGettingInvalidInput_shouldThrowIllegalArgumentException(String value) {
        assertThrows(IllegalArgumentException.class,
            () -> new ArgumentsParser(value)
        );
    }

    private static Stream<Arguments> getInvalidInputArguments() {
        String input1
            = "java -jar nginx-log-stats.jar --from 2023-08-31 --to 2030-01-20 --format markdown";
        String input2
            = "java -jar nginx-log-stats.jar --from 2023-08-31 --to 2030-01-20";
        String input3 = "Shrek";

        return Stream.of(
            Arguments.of(input1),
            Arguments.of(input2),
            Arguments.of(input3)
        );
    }

    private static Stream<Arguments> getEmptyInputArguments() {
        return Stream.of(
            Arguments.of((Object) null),
            Arguments.of("    "),
            Arguments.of("")
        );
    }

    private static Stream<Arguments> getCorrectArguments() {
        DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);

        String input1 =
            "java -jar nginx-log-stats.jar --path logs/2023* --from 2023-08-31 --to 2030-01-20 --format markdown";
        String input2
        = "java -jar nginx-log-stats.jar --path "
        + "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs  "
            + "--format adoc";

        ParsedInput parsedInput1 = ParsedInput.builder()
            .path("logs/2023*")
            .from(OffsetDateTime.of(LocalDate.parse("2023-08-31", formatter), LocalTime.MIN, ZoneOffset.UTC))
            .to(OffsetDateTime.of(LocalDate.parse("2030-01-20", formatter), LocalTime.MAX, ZoneOffset.UTC))
            .format("markdown")
            .build();

        ParsedInput parsedInput2 = ParsedInput.builder()
            .path("https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs")
            .from(OffsetDateTime.MIN)
            .to(OffsetDateTime.MAX)
            .format("adoc")
            .build();

        return Stream.of(
            Arguments.of(input1, parsedInput1),
            Arguments.of(input2, parsedInput2)
        );
    }
}
