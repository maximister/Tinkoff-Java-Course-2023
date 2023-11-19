package edu.project3.input_arguments_parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;

//тут как-то в лоб сделал, возможно стоит потом переделать поизящнее
public class ArgumentsParser {
    private final String unparsedInput;
    @Getter private final ParsedInput parsedInput;

    private final static Pattern ARGUMENT_PATTERN
        = Pattern.compile("--(path|from|to|format)\\s(\\S+)");
    private static final DateTimeFormatter DATE_TIME_FORMATTER
        = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);

    private static final String INVALID_INPUT_MESSAGE
        = "Invalid command! Please check your command and try again";

    public ArgumentsParser(String input) {
        if (input == null || input.isEmpty() || input.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT_MESSAGE);
        }
        unparsedInput = input;
        parsedInput = parseInput();
    }

    private ParsedInput parseInput() {
        Matcher matcher = ARGUMENT_PATTERN.matcher(unparsedInput);
        String path = null;
        OffsetDateTime from = OffsetDateTime.MIN;
        OffsetDateTime to = OffsetDateTime.MAX;
        String format = null;

        while (matcher.find()) {
            String command = matcher.group(1);
            String value = matcher.group(2);
            switch (command) {
                case "path":
                    path = value;
                    break;
                case "from":
                    from = OffsetDateTime.of(LocalDate.parse(value, DATE_TIME_FORMATTER),
                        LocalTime.MIN, ZoneOffset.UTC);
                    break;
                case "to":
                    to = OffsetDateTime.of(LocalDate.parse(value, DATE_TIME_FORMATTER),
                        LocalTime.MIN, ZoneOffset.UTC);
                    break;
                case "format":
                    format = value;
                    break;
                default:
                    break;
            }
        }

        if (path == null || format == null) {
            throw new IllegalArgumentException(INVALID_INPUT_MESSAGE);
        }
        return ParsedInput.builder()
            .path(path)
            .from(from)
            .to(to)
            .format(format)
            .build();
    }
}
