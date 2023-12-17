package edu.hw10.task1.fieldGenerators;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import java.lang.annotation.Annotation;
import java.util.Random;

public class IntegerFieldGenerator implements RandomFieldValueGenerator {
    @Override
    public Object generate(Annotation[] annotations) {
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;

        for (Annotation annotation : annotations) {
            if (annotation instanceof Min) {
                min = (int) ((Min) annotation).value();
            }
            if (annotation instanceof Max) {
                max = (int) ((Max) annotation).value();
            }
        }
        Random random = new Random();

        return random.nextInt(min, max);
    }
}
