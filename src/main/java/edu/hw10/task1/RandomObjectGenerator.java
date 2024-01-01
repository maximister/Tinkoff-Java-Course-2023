package edu.hw10.task1;

import edu.hw10.task1.fieldGenerators.RandomParameterGenerator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Random;

//постарался исправить все недостатки ранее описанной версии
public class RandomObjectGenerator {

    private final RandomParameterGenerator fieldGenerator;

    public RandomObjectGenerator(RandomParameterGenerator fieldGenerator) {
        this.fieldGenerator = fieldGenerator;
    }

    public <T> T nextObject(Class<T> requiredClass) {
        Constructor<?> constructor = getRandomConstructor(requiredClass);
        Parameter[] parameters = constructor.getParameters();
        Object[] objectList = generateRandomObjects(parameters);

        try {
            //на случай, если в классе приватный конструктор и какой-нибудь фабричный метод, но мы вызвали не тот метод)
            constructor.setAccessible(true);
            return requiredClass.cast(constructor.newInstance(objectList));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            constructor.setAccessible(false);
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
        return constructors[new Random().nextInt(constructors.length)];
    }

    private Object[] generateRandomObjects(Parameter[] parameters) {
        Object[] objectList = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            objectList[i] = fieldGenerator.generate(parameters[i]);
        }
        return objectList;
    }
}

