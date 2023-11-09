package edu.hw5.task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public final class Friday13Seeker {

    private final static int FRIDAY13 = 13;
    private final static String ILLEGAL_YEAR_MESSAGE = "I'm sorry, but we don't work with the times of your youth(";

    private Friday13Seeker() {
    }

    public static List<LocalDate> findAllFriday13(int year) {
        if (year < 0) {
            throw new IllegalArgumentException(ILLEGAL_YEAR_MESSAGE);
        }

        List<LocalDate> fridayList = new ArrayList<>();

        LocalDate date = LocalDate.of(year, Month.JANUARY, FRIDAY13);
        while (date.getYear() == year) {
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridayList.add(date);
            }

            date = date.plusMonths(1);
        }

        return fridayList;
    }

    public static LocalDate getNextFriday13(LocalDate currentDate) {
        return currentDate.with(nextFriday13());
    }

    private static TemporalAdjuster nextFriday13() {
        return (temporal) -> {
            LocalDate currentFriday = LocalDate.from(temporal.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)));
            while (currentFriday.getDayOfMonth() != FRIDAY13) {
                currentFriday = currentFriday.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
            }
            return currentFriday;
        };
    }
}
