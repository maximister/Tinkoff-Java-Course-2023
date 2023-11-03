package edu.project2.solver;

import edu.project2.structures.Cell;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DfsSolver implements Solver {
    private static final int[][] MOVES_TO_NEIGHBOURS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    private Maze maze;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        this.maze = maze;
        Cell startCell = maze.getCell(start.row(), start.col());
        Cell endCell = maze.getCell(end.row(), end.col());
        maze.resetVisitedCells();

        List<Coordinate> path = new ArrayList<>();
        Map<Cell, Cell> pathMap = new HashMap<>();
        Stack<Cell> cellStack = new Stack<>();
        cellStack.push(startCell);

        while (!cellStack.isEmpty()) {
            Cell currentCell = cellStack.pop();
            if (currentCell == endCell) {
                break;
            }

            List<Cell> neighbours = getNeighbour(currentCell);
            if (neighbours == null) {
                continue;
            }

            for (int i = 0; i < neighbours.size(); i++) {
                Cell neighbour = neighbours.get(i);
                pathMap.put(neighbour, currentCell);

                neighbour.visitThisCell();
                cellStack.push(neighbour);
            }
        }

        Cell currentCell = endCell;
        while (currentCell != startCell) {
            path.add(new Coordinate(currentCell.getRow(), currentCell.getCol()));
            currentCell = pathMap.get(currentCell);
        }
        path.add(new Coordinate(startCell.getRow(), startCell.getCol()));

        return path;
    }

    private List<Cell> getNeighbour(Cell cell) {
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
}
