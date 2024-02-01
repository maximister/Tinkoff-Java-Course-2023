package edu.project2.structures;

import org.jetbrains.annotations.NotNull;

public record Coordinate(int row, int col) implements Comparable<Coordinate> {
    public boolean isValidCoordinate(int width, int height) {
        return (row >= 0 && row < height) && (col >= 0 && col < width);
    }

    @Override
    public int compareTo(@NotNull Coordinate o) {
        if (o.row == row() && o.col == col()) {
            return 0;
        } else if (row() + col() > o.row + o.col) {
            return 1;
        } else {
            return -1;
        }
    }
}
