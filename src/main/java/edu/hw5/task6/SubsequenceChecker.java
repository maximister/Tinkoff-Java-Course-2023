package edu.hw5.task6;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SubsequenceChecker {
    private SubsequenceChecker() {
    }

    public static boolean isSubsequence(String origin, String subsequence) {
        StringBuilder regexp = new StringBuilder(".*");
        for (var letter : subsequence.toCharArray()) {
            regexp.append(letter);
            regexp.append(".*");
        }
        Pattern pattern = Pattern.compile(regexp.toString());
        Matcher matcher = pattern.matcher(origin);

        return matcher.matches();
    }
}
