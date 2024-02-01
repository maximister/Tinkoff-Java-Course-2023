package edu.project2.generator;

import edu.project2.structures.Cell;
import edu.project2.structures.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class AbstractGenerator implements Generator {
    protected static final Random RANDOM = new Random();
    protected Maze maze;
    protected static final int[][] MOVES_TO_NEIGHBOURS = {{0, -2}, {0, 2}, {-2, 0}, {2, 0}};

    protected List<Cell> getNeighbours(int row, int col) {
        List<Cell> neighbours = new ArrayList<>();

        for (int[] move : Arrays.asList(MOVES_TO_NEIGHBOURS)) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            if (maze.isValidCell(newRow, newCol) && !maze.getCell(newRow, newCol).isVisited()) {
                Cell neighbour = maze.getCell(newRow, newCol);
                neighbours.add(neighbour);
            }
        }

        return neighbours;
    }

    protected Cell getRandomNeighbour(int row, int col) {
        List<Cell> neighbours = getNeighbours(row, col);

        if (!neighbours.isEmpty()) {
            return neighbours.get(RANDOM.nextInt(neighbours.size()));
        }

        return null;
    }

    protected void destroyWall(Cell firstCell, Cell secondCell) {
        int wallRow = (firstCell.getRow() + secondCell.getRow()) / 2;
        int wallCol = (firstCell.getCol() + secondCell.getCol()) / 2;

        Cell destroyedWall = maze.getCell(wallRow, wallCol);
        destroyedWall.setType(Cell.Type.PASSAGE);
        destroyedWall.visitThisCell();
    }

    protected void destroyWall(Cell wall) {
        wall.setType(Cell.Type.PASSAGE);
        wall.visitThisCell();
    }

    public static Random getRandomForTests() {
        return RANDOM;
    }
}
