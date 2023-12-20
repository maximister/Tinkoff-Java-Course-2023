package edu.project4.transformations;

import edu.project4.model.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathTransformationTest {
    //по идее тут просто нужно проверить что я правильно переписал формулы
    private static final Point TEST_POINT = new Point(28.1, 24.2);

    @Test
    @DisplayName("testing disk transformation function")
    public void diskTransformationTest() {
        assertEquals(applyTransformation(new DiskTransformation()), new Point(-5928,-21841));
    }

    @Test
    @DisplayName("testing handkerchief transformation function")
    public void handkerchiefTransformationTest() {
        assertEquals(applyTransformation(new HandkerchiefTransformation()), new Point(899798,356266));
    }

    @Test
    @DisplayName("testing heart transformation function")
    public void heartTransformationTest() {
        assertEquals(applyTransformation(new HeartTransformation()), new Point(3498844,-1229060));
    }

    @Test
    @DisplayName("testing hyperbolic transformation function")
    public void hyperbolicTransformationTest() {
        assertEquals(applyTransformation(new HyperbolicTransformation()), new Point(2043,2420000));
    }

    @Test
    @DisplayName("testing linear transformation function")
    public void linearTransformationTest() {
        assertEquals(applyTransformation(new LinearTransformation()), new Point(2810000,2420000));
    }

    @Test
    @DisplayName("testing sinusoidal transformation function")
    public void sinusoidalTransformationTest() {
        assertEquals(applyTransformation(new SinusoidalTransformation()), new Point(17345,-80326));
    }

    @Test
    @DisplayName("testing spherical transformation function")
    public void sphericalTransformationTest() {
        assertEquals(applyTransformation(new SphericalTransformation()), new Point(2043,1760));
    }

    @Test
    @DisplayName("testing swirl transformation function")
    public void swirlTransformationTest() {
        assertEquals(applyTransformation(new SwirlTransformation()), new Point(-3692669,341611));
    }



    public static Point applyTransformation(Transformation transformation) {
        //не знаю, насколько это корректно для тестов,
        //но я решил сравнивать до первых 5 знаков после запятой
        Point res = transformation.apply(TEST_POINT);
        return new Point(
            Math.round(res.x() * 100_000),
            Math.round(res.y() * 100_000)
        );
    }
}
