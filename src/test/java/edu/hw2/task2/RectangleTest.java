package edu.hw2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

public class RectangleTest {
    @ParameterizedTest
    @MethodSource("rectangleTestArgs")
    @DisplayName("Проверка функционала класса Rectangle")
    public void rectangleTest(Rectangle rec, double area) {
        assertThat(rec.area()).isEqualTo(area);
    }

    public static Stream<Arguments> rectangleTestArgs() {
        Rectangle rec = new Rectangle(4, 5);
        return Stream.of(
            Arguments.of(new Rectangle(), 0),
            Arguments.of(rec, 20),
            Arguments.of(rec.setWidth(12), 60),
            Arguments.of(rec.setHeight(10), 40)
        );
    }
}
