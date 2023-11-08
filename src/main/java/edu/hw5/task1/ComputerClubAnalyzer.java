package edu.hw5.task1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComputerClubAnalyzer {

    //2022-03-12, 23:50
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
    private final static Pattern PATTERN =
        Pattern.compile("^(\\d{4}-\\d{2}-\\d{2}, \\d{2}:\\d{2}) - (\\d{4}-\\d{2}-\\d{2}, \\d{2}:\\d{2})$");
    private final static String INVALID_DATE_MESSAGE = "Invalid date format: ";
    private final static String NEGATIVE_DURATION_MESSAGE = "duration of your session is negative";

    private ComputerClubAnalyzer() {
    }

    //для вывода
    @SuppressWarnings("checkstyle:MagicNumber")
    public static String getAverageSessionDurationForPrint(List<String> sessions) {
        Long averageDurationInMinutes = getAverageDurationOfSession(sessions).toMinutes();

        return String.format("average duration is: %sh %sm",
            averageDurationInMinutes / 60, averageDurationInMinutes % 60);
    }

    public static Duration getAverageDurationOfSession(List<String> sessions) {
        return sessions.stream()
            .map(ComputerClubAnalyzer::parseSession)
            .map(ComputerClubAnalyzer::getSessionsDuration)
            .reduce(Duration.ZERO, Duration::plus)
            .dividedBy(sessions.size());
    }

    private static List<LocalDateTime> parseSession(String session) {
        Matcher matcher = PATTERN.matcher(session);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE + session);
        }

        return List.of(LocalDateTime.parse(matcher.group(1), FORMATTER),
            LocalDateTime.parse(matcher.group(2), FORMATTER));
    }

    private static Duration getSessionsDuration(List<LocalDateTime> session) {
        Duration sessionDuration = Duration.between(session.get(0), session.get(1));

        if (sessionDuration.isNegative()) {
            throw new IllegalArgumentException(NEGATIVE_DURATION_MESSAGE);
        }

        return sessionDuration;
    }
}
