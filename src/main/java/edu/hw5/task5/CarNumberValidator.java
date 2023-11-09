package edu.hw5.task5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CarNumberValidator {
    private final static Pattern PATTERN = Pattern.compile("^[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{3}$");

    private CarNumberValidator() {
    }

    public static boolean isValidNumber(String number) {
        Matcher matcher = PATTERN.matcher(number);
        return matcher.matches();
    }
}
