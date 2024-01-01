package edu.hw10.task1.fieldGenerators;

import java.util.Random;

//в результате попыток сделать приемлемый генератор, я уперся в 2 проблемы рандома:
//нет методов nextByte и nextShort, что немного ломало мою логику и вынуждало делать кучу костылей
//методы next рандома в качестве границ принимают примитивы, что вызывало проблемы при рефлексии
//и заставляло оборачивать и разворачивать поля, поэтому я решил добавить нужные мне методы next,
//принимающие оберточные классы
//PS дальше были еще проблемы, поэтому теперь оно ест Objectы

//соответственно, все, что выходит за рамки задачи, но по-хорошему должно было быть перегружено,
//тут я не трогал, чтобы не смещать акцент с основной учебной задачи скажем так)

public class RandomForFields extends Random {
    private final Random random;

    public RandomForFields(Random random) {
        this.random = random;
    }

    public int nextInteger(Object origin, Object bound) {
        return random.nextInt(Integer.parseInt(origin.toString()), Integer.parseInt(bound.toString()));
    }

    public byte nextByte(Object origin, Object bound) {
        return (byte) random.nextInt(Byte.parseByte(origin.toString()), Byte.parseByte(bound.toString()));
    }

    public short nextShort(Object origin, Object bound) {
        return (short) random.nextInt(Short.parseShort(origin.toString()), Short.parseShort(bound.toString()));
    }

    public char nextCharacter(Object origin, Object bound) {
        return (char) random.nextInt(origin.toString().charAt(0), bound.toString().charAt(0));
    }

    public long nextLong(Object origin, Object bound) {
        return random.nextLong(Long.parseLong(origin.toString()), Long.parseLong(bound.toString()));
    }

    public double nextDouble(Object origin, Object bound) {
        return random.nextDouble(Double.parseDouble(origin.toString()), Double.parseDouble(bound.toString()));
    }

    public float nextFloat(Object origin, Object bound) {
        return random.nextFloat(Float.parseFloat(origin.toString()), Float.parseFloat(bound.toString()));
    }

    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    //по идее по хорошему нужно перегрузить все next методы с разными аргументами, чтобы они вызывали
    //соответствующие методы поля random, но я это опущу для читаемости и понятности
    //(плюс очевидно, что кроме этой единственной задачи класс нигде не будет никогда использоваться,
    //поэтому такие допущения)
}
