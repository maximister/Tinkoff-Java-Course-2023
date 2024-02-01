package edu.hw10.task1.fieldGenerators;

import java.lang.reflect.Parameter;

public interface RandomParameterGenerator {
    Object generate(Parameter parameter);
}
