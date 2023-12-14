package edu.project4.utils;

import edu.project4.model.Pixel;
import edu.project4.model.Point;
import edu.project4.model.Rect;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalFrameUtils {
    public static Point getRandomPoint(Rect rect) {
        return new Point(
            rect.x() + ThreadLocalRandom.current().nextDouble() * rect.width(),
            rect.y() + ThreadLocalRandom.current().nextDouble() * rect.height()
        );
    }

    public static <T> T getRandomListElement(List<T> list) {
        return list.get((int)
            (ThreadLocalRandom.current().nextDouble() * list.size()));
    }

    public static Point rotatePoint(Rect world, Point point, double theta) {
        double centerX = world.x() + world.width() / 2;
        double centerY = world.y() + world.height() / 2;
        double x = (point.x() - centerX) * Math.cos(theta)
            - (point.y() - centerY) * Math.sin(theta) + centerX;
        double y = (point.x() - centerX) * Math.sin(theta)
            + (point.y() - centerY) * Math.cos(theta) + centerY;
        return new Point(x, y);
    }
}
