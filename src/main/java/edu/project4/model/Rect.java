package edu.project4.model;

public record Rect(double x, double y, double width, double height) {
    boolean contains(Point p) {
        return false;
    }
}

