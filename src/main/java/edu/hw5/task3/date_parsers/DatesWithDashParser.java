package edu.hw5.task3.date_parsers;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*брабатывает даты вида
    2020-10-10
    2020-12-2*/
public class DatesWithDashParser extends AbstractDateParser {

    public DatesWithDashParser() {
        parsingPattern = Pattern.compile("(\\d{4})-(\\d{1,2})-(\\d{1,2})");
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public Optional<LocalDate> parseDate(String date) {
        Matcher matcher = parsingPattern.matcher(date);
        if (!matcher.matches()) {
            return parseByNextParser(date);
        }
        int day = Integer.parseInt(matcher.group(3));
        int month = Integer.parseInt(matcher.group(2));
        int year = Integer.parseInt(matcher.group(1));

        return Optional.of(LocalDate.of(year, month, day));
    }
}
