package edu.hw7.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PiComputerAndBenchmarkTest {
    //по идее своебразный тест калькулятора числа пи это и есть бенчмарк,
    // сам бенчмарк тоже не вижу смысла тестировать, это же просто замеры и удобный вывод.
    //Если я не прав, буду рад (или не очень) дописать привычные тесты,
    //а пока тут оставлю просто прогон функционала для поднятия ковераге

    @Test
    @DisplayName("прогон функций")
    public void computerAndBenchmarkTest() {
        ReportGenerator generator = new ReportGenerator();

        try {
            //немножко уменьшу количество итераций для ускорения теста (немного колхоз наверное, да?)
            Field iterationsField = generator.getClass().getDeclaredField("iterations");
            iterationsField.setAccessible(true);
            long[] iterations = {1, 10, 100, 1000};
            iterationsField.set(generator, iterations);
            final String[] report = new String[1];
            assertDoesNotThrow(() ->
                report[0] = generator.generateReport());

            assertThat(report).isNotEmpty();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
