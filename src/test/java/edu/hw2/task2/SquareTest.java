package edu.hw2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SquareTest {
    @ParameterizedTest
    @MethodSource("squareTestArgs")
    @DisplayName("Проверка функционала класса Square")
    public void squareTest(Rectangle rec, double area) {
        assertThat(rec.area()).isEqualTo(area);
    }

    public static Stream<Arguments> squareTestArgs() {
        Square sq = new Square(4, 5);
        return Stream.of(
            Arguments.of(new Square(), 0),
            Arguments.of(sq, 20),
            Arguments.of(sq.setWidth(12), 60),
            Arguments.of(sq.setHeight(10), 40),
            Arguments.of(sq.setSide(10), 100)
        );
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    @DisplayName("Основной тест")
    void rectangleArea(Rectangle rect) {
        rect = rect.setWidth(20);
        rect = rect.setHeight(10);

        assertThat(rect.area()).isEqualTo(200.0);
    }

    static Arguments[] rectangles() {
        return new Arguments[]{
            Arguments.of(new Rectangle()),
            Arguments.of(new Square())
        };
    }

}
