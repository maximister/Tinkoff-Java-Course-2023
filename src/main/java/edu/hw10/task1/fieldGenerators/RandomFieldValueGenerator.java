package edu.hw10.task1.fieldGenerators;

import java.lang.annotation.Annotation;

public interface RandomFieldValueGenerator {
    //я пытался передавать Field в метод и уже оттуда получать аннотации и остальную нужную инфу,
    //плюс хотел вынести общую логику в абстрактный класс, но у меня возникли проблемы с определением типа поля
    //я хотел, чтобы если поступивший Field это число, то отделый метод узнает его тип и вернет
    // соответствующие максимум и минимум (например Integer.MAX_VALUE для Integer и int),
    //но не смог адекватно определить класс и его принадлежность к числам, если есть идеи - буду рад помощи
    Object generate(Annotation[] annotations);
}
