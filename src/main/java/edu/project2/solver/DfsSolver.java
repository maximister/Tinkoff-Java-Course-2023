package edu.project2.solver;

import edu.project2.structures.Cell;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DfsSolver extends AbstractSolver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        this.maze = maze;
        Cell startCell = maze.getCell(start.row(), start.col());
        Cell endCell = maze.getCell(end.row(), end.col());
        maze.resetVisitedCells();

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

        return getPathFromMap(pathMap, startCell, endCell);
    }
}
