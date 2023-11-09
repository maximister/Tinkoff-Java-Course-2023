package edu.hw5.task4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PasswordValidator {
    private PasswordValidator() {
    }

    /*
    все пароли содержали ХОТЯ БЫ один из следующих символов
    ...
    Напишите регулярное выражение, которое возвращает true тогда и только тогда,
     когда пароль содержит ОДИН из требуемых символов.

     не совсем понял, нужен ровно 1 символ или хотя бы 1, поэтому реализовал оба варианта
     */

    public static boolean isValidPassword(String password) {
        Pattern passwordPattern = Pattern.compile("^.*[~!@#$%^&*|]+.*$");
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidPasswordVersion2(String password) {
        Pattern passwordPattern = Pattern.compile("^\\w*[~!@#$%^&*|]{1}\\w*$");
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }
}
