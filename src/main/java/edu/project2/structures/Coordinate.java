package edu.project2.structures;

public record Coordinate(int row, int col) {
    public boolean isValidCoordinate(int width, int height) {
        return (row >= 0 && row < height) && (col >= 0 && col < width);
    }
}
