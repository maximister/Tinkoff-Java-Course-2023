package edu.hw5.task3.date_parsers;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

public abstract class AbstractDateParser {
    private AbstractDateParser nextParser;
    protected Pattern parsingPattern;

    public void setNextParser(AbstractDateParser nextParser) {
        this.nextParser = nextParser;
    }

    public abstract Optional<LocalDate> parseDate(String date);

    protected Optional<LocalDate> parseByNextParser(String date) {
        if (nextParser != null) {
            return nextParser.parseDate(date);
        }

        return Optional.empty();
    }
}
