package edu.project4.transformations;

import edu.project4.model.Point;

public class SphericalTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double radius = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double x = (1 / Math.pow(radius, 2)) * point.x();
        double y = (1 / Math.pow(radius, 2)) * point.y();
        return new Point(x, y);
    }
}
