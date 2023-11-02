package edu.project2.structures;

public final class Maze {
    private final int height;

    private final int width;

    private final Cell[][] grid;

    private static final int MIN_SIDE_SIZE = 5;

    public Maze(int height, int width) {
        if (!isValidParameters(height, width)) {
            throw new IllegalArgumentException("Your maze must be 5x5 or bigger!");
        }

        this.height = height;
        this.width = width;

        grid = new Cell[height][width];

        /*
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col, Cell.Type.WALL);
            }
        }
        */

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if ((row % 2 != 0 && col % 2 != 0)
                    && (row < height - 1 && col < width - 1)) {
                    grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE);
                } else {
                    grid[row][col] = new Cell(row, col, Cell.Type.WALL);
                }
            }
        }
    }

    private boolean isValidParameters(int height, int width) {
        return height >= MIN_SIDE_SIZE && width >= MIN_SIDE_SIZE;
    }

    public Cell getCell(int row, int col) {
        if (!isValidCell(row, col)) {
            throw new IllegalArgumentException("Invalid Cell coordinates!");
        }

        return grid[row][col];
    }

    public void setCell(int row, int col, Cell.Type type) {
        if (!isValidCell(row, col)) {
            throw new IllegalArgumentException("Invalid Cell coordinates!");
        }

        grid[row][col] = new Cell(row, col, type);
    }

    public boolean isValidCell(int row, int col) {
        return (row > 0 && row <= height - 1) && (col > 0 && col <= width - 1);
    }

    //TODO: удалить потом
    public void testPrint() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell c = grid[i][j];
                char s;
                if (c.getType() == Cell.Type.WALL) {
                    s = '□';
                } else {
                    s = ' ';
                }

                System.out.print(s + "  ");
            }
            System.out.println();
        }
    }
}
