package edu.hw10.task1.fieldGenerators;

import java.lang.annotation.Annotation;
import java.util.Random;

public class BooleanFieldGenerator implements RandomFieldValueGenerator {
    @Override
    public Object generate(Annotation[] annotations) {
        return new Random().nextBoolean();
    }
}
