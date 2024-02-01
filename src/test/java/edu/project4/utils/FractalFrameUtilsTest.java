package edu.project4.utils;

import edu.project4.model.Point;
import edu.project4.model.Rect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FractalFrameUtilsTest {
    @Test
    @DisplayName("testing canvas random point generator")
    public void getRandomPoint_shouldReturnPointFromCanvas() {
        Rect canvas = new Rect(0, 0, 100, 100);

        Point randomPoint = FractalFrameUtils.getRandomPoint(canvas);

        assertTrue(canvas.contains(randomPoint));
    }

    @Test
    @DisplayName("testing list random element getter")
    public void getRandomListElement_shouldReturnElementFromList() {
        List<Integer> testList = List.of(1, 2, 3, 4);

        Integer randomInt = FractalFrameUtils.getRandomListElement(testList);

        assertTrue(testList.contains(randomInt));
    }

    @Test
    @DisplayName("testing point rotate method")
    public void rotatePoint_shouldReturnCorrectPoint() {
        //Проверяем корректность точки из следующих соображений:
        //точка должна быть в пределах холста, и, если повернуть ее достаточное количество раз,
        //она вернется на место (то есть пройдет всю окружность). Для угла п это 2 поворота
        Rect canvas = new Rect(0, 0, 100, 100);
        double theta = Math.PI;
        //погрешность, тк числа с плавающей точкой, очевидно, неточные
        double eps = 0.000001;

        Point initialPoint = FractalFrameUtils.getRandomPoint(canvas);
        Point rotatedPoint = FractalFrameUtils.rotatePoint(canvas, initialPoint, theta);

        assertTrue(canvas.contains(rotatedPoint));

        rotatedPoint = FractalFrameUtils.rotatePoint(canvas, rotatedPoint, theta);
        assertEquals(rotatedPoint.x(), initialPoint.x(), eps);
        assertEquals(rotatedPoint.y(), initialPoint.y(), eps);
    }
}
