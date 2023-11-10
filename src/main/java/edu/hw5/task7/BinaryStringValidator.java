package edu.hw5.task7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BinaryStringValidator {

    private final static Pattern PATTERN1 = Pattern.compile("^[01]{2}0[01]*$");
    private final static Pattern PATTERN2 = Pattern.compile("^1[10]*1|0[10]*0|[01]$");
    private final static Pattern PATTERN3 = Pattern.compile("^[10]{1,3}$");

    private BinaryStringValidator() {
    }

    //Насколько адекватный нейминг? или перебор?
    public static boolean doesContainsAtLeastThreeSymbolsAndThirdIsZero(String binaryString) {
        Matcher matcher = PATTERN1.matcher(binaryString);
        return matcher.matches();
    }

    public static boolean isFirstSymbolEqualsToLast(String binaryString) {
        //тут думаю есть более элегантное решение, но пока так)
        Matcher matcher = PATTERN2.matcher(binaryString);
        return matcher.matches();
    }

    public static boolean isLengthMoreThanOneAndLessThanThree(String binaryString) {
        Matcher matcher = PATTERN3.matcher(binaryString);
        return matcher.matches();
    }
}
