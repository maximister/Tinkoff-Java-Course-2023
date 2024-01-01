package edu.hw10.task1.fieldGenerators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Random;
import org.apache.commons.lang3.ClassUtils;

public abstract class AbstractParameterGenerator implements RandomParameterGenerator {

    private final long defaultMaxStringLength = 64;
    private final long defaultMinStringLength = 1;
    protected final RandomForFields random;

    public AbstractParameterGenerator(Random random) {

        this.random = new RandomForFields(random);
    }

    protected final Object getRandomValue(Parameter parameter)
        throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Class<?> wrappedFieldClass = ClassUtils.primitiveToWrapper(parameter.getType());

        if (Boolean.class.isAssignableFrom(wrappedFieldClass)) {
            return random.nextBoolean();
        } else if (String.class.isAssignableFrom(wrappedFieldClass)) {
            return getRandomString(parameter);
        } else {
            Object[] minMaxValues = getMinMaxFieldValues(parameter);
            Object min = minMaxValues[0];
            Object max = minMaxValues[1];

            Method getRandomField = random.getClass()
                .getMethod(
                    getMethodNameForRandom(wrappedFieldClass),
                    Object.class,
                    Object.class
                );

            return getRandomField.invoke(random, min, max);
        }
    }

    private Object getRandomString(Parameter parameter) throws IllegalAccessException, NoSuchFieldException {
        StringBuilder generatedString = new StringBuilder();
        Object[] minMaxValues = getMinMaxFieldValues(parameter);
        long length = random.nextLong(minMaxValues[0], minMaxValues[1]);

        for (int i = 0; i < length; i++) {
            generatedString.append(random.nextCharacter(Character.MIN_VALUE, Character.MAX_VALUE));
        }

        return generatedString.toString();
    }

    private String getMethodNameForRandom(Class<?> clazz) {
        String className = clazz.toString();
        return "next" + className.substring(className.lastIndexOf('.') + 1);
    }

    protected Object[] getMinMaxFieldValues(Parameter parameter) throws IllegalAccessException, NoSuchFieldException {
        //передаю поле, а не класс и дублирую эту операцию из-за необходимости в будущем вытащить из поля аннотации
        Class<?> clazz = ClassUtils.primitiveToWrapper(parameter.getType());
        if (Number.class.isAssignableFrom(clazz)) {
            return ParameterGeneratorUtils.getMinMaxNumberValues(clazz);
        } else if (Character.class.isAssignableFrom(clazz)) {
            return new Object[] {Character.MIN_VALUE, Character.MAX_VALUE};
        } else if (String.class.isAssignableFrom(clazz)) {
            return new Object[] {defaultMinStringLength, defaultMaxStringLength};
        } else {
            throw new IllegalAccessException("Unsupportable field type: " + clazz);
        }
    }
}
