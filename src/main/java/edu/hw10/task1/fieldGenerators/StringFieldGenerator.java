package edu.hw10.task1.fieldGenerators;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;
import java.lang.annotation.Annotation;
import java.util.Random;

public class StringFieldGenerator implements RandomFieldValueGenerator {
    private static final int MIN_STRING_LENGTH = 1;
    private static final int MAX_STRING_LENGTH = 30;
    private static final char[] ALPHABET =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Override
    public Object generate(Annotation[] annotations) {
        long min = MIN_STRING_LENGTH;
        long max = MAX_STRING_LENGTH;
        boolean canReturnNull = true;
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (Annotation annotation : annotations) {
            if (annotation instanceof Min) {
                min = ((Min) annotation).value();
            }
            if (annotation instanceof Max) {
                max = ((Max) annotation).value();
            }

            long size = random.nextLong(min, max);
            for (int i = 0; i < size; i++) {
                stringBuilder.append(ALPHABET[random.nextInt(ALPHABET.length)]);
            }

            if (annotation instanceof NotNull) {
                canReturnNull = false;
            }
        }

        if (canReturnNull) {
            return random.nextBoolean() ? stringBuilder.toString() : null;
        } else {
            return stringBuilder.toString();
        }
    }
}
