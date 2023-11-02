package edu.project2.structures;

public class Cell {
    private final int row;
    private final int col;
    private boolean isVisited;
    private Type type;

    public enum Type { WALL, PASSAGE }

    public Cell(int row, int col, Type type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public void visitThisCell() {
        isVisited = true;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
