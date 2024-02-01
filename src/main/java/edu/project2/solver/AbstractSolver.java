package edu.project2.solver;

import edu.project2.structures.Cell;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class AbstractSolver implements Solver {
    protected static final int[][] MOVES_TO_NEIGHBOURS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    protected Maze maze;

    protected List<Cell> getNeighbour(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();
        List<Cell> neighbours = new ArrayList<>();

        for (int[] move : Arrays.asList(MOVES_TO_NEIGHBOURS)) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            if (maze.isValidCell(newRow, newCol) && !maze.getCell(newRow, newCol).isVisited()
                && maze.getCell(newRow, newCol).getType() != Cell.Type.WALL) {
                Cell neighbour = maze.getCell(newRow, newCol);
                neighbours.add(neighbour);
            }
        }

        if (!neighbours.isEmpty()) {
            return neighbours;
        }

        return null;
    }

    protected List<Coordinate> getPathFromMap(Map<Cell, Cell> pathMap, Cell startCell, Cell endCell) {
        List<Coordinate> path = new ArrayList<>();

        Cell currentCell = endCell;
        while (currentCell != startCell) {
            path.add(new Coordinate(currentCell.getRow(), currentCell.getCol()));
            currentCell = pathMap.get(currentCell);
        }
        path.add(new Coordinate(startCell.getRow(), startCell.getCol()));

        return path;
    }
}
