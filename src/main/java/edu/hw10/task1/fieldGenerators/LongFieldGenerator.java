package edu.hw10.task1.fieldGenerators;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import java.lang.annotation.Annotation;
import java.util.Random;

public class LongFieldGenerator implements RandomFieldValueGenerator {
    @Override
    public Object generate(Annotation[] annotations) {
        long min = Long.MIN_VALUE;
        long max = Long.MAX_VALUE;

        for (Annotation annotation : annotations) {
            if (annotation instanceof Min) {
                min = ((Min) annotation).value();
            }
            if (annotation instanceof Max) {
                max = ((Max) annotation).value();
            }
        }

        return new Random().nextLong(min, max);
    }
}
