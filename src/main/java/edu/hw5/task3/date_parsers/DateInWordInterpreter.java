package edu.hw5.task3.date_parsers;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*Обрабатывает даты вида
    tomorrow
    today
    yesterday
    day before yesterday
    day after tomorrow
    NUMBER day(s) ago
    NUMBER day(s) after/next/lately*/
public class DateInWordInterpreter extends AbstractDateParser {

    @Override
    public Optional<LocalDate> parseDate(String date) {
        LocalDate currentDate = LocalDate.now();
        //решение красивее и интереснее не придумал, буду рад советам
        return switch (date) {
            case "tomorrow" -> Optional.of(currentDate.plusDays(1));
            case "today" -> Optional.of(currentDate);
            case "yesterday" -> Optional.of(currentDate.minusDays(1));
            default -> {
                if (date.matches("^(([tT]he )?)[dD]ay before yesterday$")) {
                    yield Optional.of(currentDate.minusDays(2));
                } else if (date.matches("^(([tT]he )?)[dD]ay after tomorrow$")) {
                    yield Optional.of(currentDate.plusDays(2));
                }
                yield parseDateInSomeDayAgoFormat(date);
            }
        };
    }

    private Optional<LocalDate> parseDateInSomeDayAgoFormat(String date) {
        //тут тоже какой-то колхоз немного, буду рад советам)
        parsingPattern = Pattern.compile("^(\\d+) [dD]ays? (next|lately|after|ago)$");
        Matcher matcher = parsingPattern.matcher(date);
        if (matcher.matches()) {
            int countOfDays = Integer.parseInt(matcher.group(1));
            if (matcher.group(2).equals("ago")) {
                return Optional.of(LocalDate.now().minusDays(countOfDays));
            }
            return Optional.of(LocalDate.now().plusDays(countOfDays));
        }

        return parseByNextParser(date);
    }
}
