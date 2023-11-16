package edu.hw5.task3.date_parsers;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*Обрабатывает даты вида
    1/3/1976
    1/3/20*/
public class DatesWithSlashParser extends AbstractDateParser {
    public DatesWithSlashParser() {
        parsingPattern = Pattern.compile("^(\\d{1,2})/(\\d{1,2})/(\\d{2}|\\d{4})$");
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    public Optional<LocalDate> parseDate(String date) {
        Matcher matcher = parsingPattern.matcher(date);
        if (!matcher.matches()) {
             return parseByNextParser(date);
        }
        int day = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));
        int year = Integer.parseInt(matcher.group(3));

        /*1/3/20 как я понял, тут указан год в текущем веке*/
        final int centuryInYears = 100;
        if (year < centuryInYears) {
            year += (LocalDate.now().getYear() / centuryInYears) * centuryInYears;
        }

        return Optional.of(LocalDate.of(year, month, day));
    }
}
