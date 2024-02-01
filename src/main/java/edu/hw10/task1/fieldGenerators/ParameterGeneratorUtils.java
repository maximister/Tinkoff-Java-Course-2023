package edu.hw10.task1.fieldGenerators;

import java.lang.reflect.Field;

final class ParameterGeneratorUtils {

    private ParameterGeneratorUtils() {
    }

    static Number[] getMinMaxNumberValues(Class<?> requiredWrappedClass)
        throws NoSuchFieldException, IllegalAccessException {

        Field max = requiredWrappedClass.getField("MAX_VALUE");
        Field min = requiredWrappedClass.getField("MIN_VALUE");

        return new Number[] {(Number) min.get(null), (Number) max.get(null)};
    }
}
