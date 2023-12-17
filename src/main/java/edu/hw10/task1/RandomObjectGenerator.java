package edu.hw10.task1;

import edu.hw10.task1.fieldGenerators.BooleanFieldGenerator;
import edu.hw10.task1.fieldGenerators.DoubleFieldGenerator;
import edu.hw10.task1.fieldGenerators.IntegerFieldGenerator;
import edu.hw10.task1.fieldGenerators.LongFieldGenerator;
import edu.hw10.task1.fieldGenerators.RandomFieldValueGenerator;
import edu.hw10.task1.fieldGenerators.StringFieldGenerator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.Random;

//у меня аннотация notNull никак не обрабатывается для нестроковых полей,
// тк примитивам нельзя присвоить null, а проверку на примитивность я пока не прикрутил,
// это опчять же field по идее передавать придется
public class RandomObjectGenerator {
    //как ты видишь, все генераторы кроме строкового на 99% состоят из повторяющегося кода, это я и хочу исправить
    private static final Map<Class<?>, RandomFieldValueGenerator> GENERATORS;

    static {
        GENERATORS = Map.of(
            Boolean.class, new BooleanFieldGenerator(),
            Double.class, new DoubleFieldGenerator(),
            Integer.class, new IntegerFieldGenerator(),
            Long.class, new LongFieldGenerator(),
            String.class, new StringFieldGenerator(),
            boolean.class, new BooleanFieldGenerator(),
            double.class, new DoubleFieldGenerator(),
            int.class, new IntegerFieldGenerator(),
            long.class, new LongFieldGenerator()
        );
    }

    public RandomObjectGenerator() {
    }

    public <T> T nextObject(Class<T> requiredClass) {
        Constructor<?> constructor = getRandomConstructor(requiredClass);
        Parameter[] parameters = constructor.getParameters();
        Object[] objectList = generateRandomObjects(parameters);

        try {
            return requiredClass.cast(constructor.newInstance(objectList));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    //как я понял, по условию фабричный метод должен быть в этом классе, среди дргих искать не надо
    public <T> T nextObject(Class<T> requiredClass, String methodName) {
        Method method = null;

        for (Method classMethod : requiredClass.getMethods()) {
            if (classMethod.getName().equals(methodName)) {
                method = classMethod;
                break;
            }
        }
        if (method == null) {
            throw new IllegalArgumentException("There are no such method \"" + methodName + "\" in your class");
        }

        Parameter[] parameters = method.getParameters();
        Object[] objectList = generateRandomObjects(parameters);
        try {
            return requiredClass.cast(method.invoke(null, objectList));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Constructor<?> getRandomConstructor(Class<?> newClass) {
        Constructor<?>[] constructors = newClass.getConstructors();
        if (constructors.length == 0) {
            throw new IllegalArgumentException("There are no constructors in your class(");
        }

        return constructors[new Random().nextInt(constructors.length)];
    }

    private Object[] generateRandomObjects(Parameter[] parameters) {
        Object[] objectList = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            RandomFieldValueGenerator generator = GENERATORS.get(parameters[i].getType());
            Annotation[] annotations = parameters[i].getAnnotations();
            objectList[i] = generator.generate(annotations);
        }
        return objectList;
    }
}
