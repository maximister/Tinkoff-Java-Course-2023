package edu.hw2.task2;

public class Square extends Rectangle {

    public Square(int width, int height) {
        super(width, height);
    }

    public Square() {
        super(0, 0);
    }

    public Square(int side) {
        super(side, side);
    }

    public final Square setSide(int side) {
        return new Square(side);
    }
}
