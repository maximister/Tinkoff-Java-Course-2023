package edu.hw10.task1.fieldGenerators;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Random;
import org.apache.commons.lang3.ClassUtils;

public class AnnotatedParameterGenerator extends AbstractParameterGenerator {

    private static final int RETURN_NULL_CHANCE = 10;

    public AnnotatedParameterGenerator(Random random) {
        super(random);
    }

    @Override
    public Object generate(Parameter parameter) {
        try {

            if (parameter.getAnnotation(NotNull.class) != null
                || ClassUtils.isPrimitiveOrWrapper(parameter.getType())) {
                return getRandomValue(parameter);
            }

            if (random.nextInt() % RETURN_NULL_CHANCE == 0) {
                return null;
            } else {
                return getRandomValue(parameter);
            }

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Object[] getMinMaxFieldValues(Parameter parameter) throws NoSuchFieldException, IllegalAccessException {
        Object[] minMaxValues = super.getMinMaxFieldValues(parameter);
        if (parameter.getAnnotation(Min.class) != null) {
            minMaxValues[0] = parameter.getAnnotation(Min.class).value();
        }
        if (parameter.getAnnotation(Max.class) != null) {
            minMaxValues[1] = parameter.getAnnotation(Max.class).value();
        }

        return minMaxValues;
    }
}
