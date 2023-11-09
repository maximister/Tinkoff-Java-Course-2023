package edu.hw5.task6;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SubstringChecker {
    private SubstringChecker() {
    }

    public static boolean isSubstring(String origin, String substring) {
        Pattern pattern = Pattern.compile(substring);
        Matcher matcher = pattern.matcher(origin);

        return matcher.find();
    }
}
