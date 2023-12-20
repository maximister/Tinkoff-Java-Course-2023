package edu.project4.transformations;

import edu.project4.model.Point;

public class SwirlTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();
        double squareRadius = x * x + y * y;
        double newX = x * Math.sin(squareRadius) - y * Math.cos(squareRadius);
        double newY = x * Math.cos(squareRadius) + y * Math.sin(squareRadius);
        return new Point(newX, newY);
    }
}
