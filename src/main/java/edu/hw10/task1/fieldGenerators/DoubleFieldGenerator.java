package edu.hw10.task1.fieldGenerators;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import java.lang.annotation.Annotation;
import java.util.Random;

public class DoubleFieldGenerator implements RandomFieldValueGenerator {
    @Override
    public Object generate(Annotation[] annotations) {
        double min = Double.MIN_VALUE;
        double max = Double.MAX_VALUE;

        for (Annotation annotation : annotations) {
            if (annotation instanceof Min) {
                min = (double) ((Min) annotation).value();
            }
            if (annotation instanceof Max) {
                max = (double) ((Max) annotation).value();
            }
        }

        return new Random().nextDouble(min, max);
    }
}
