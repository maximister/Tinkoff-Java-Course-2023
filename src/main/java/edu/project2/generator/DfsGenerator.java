package edu.project2.generator;

import edu.project2.structures.Cell;
import edu.project2.structures.Maze;
import java.util.Stack;

public class DfsGenerator extends AbstractGenerator {
    @Override
    public Maze generate(int height, int width) {
        maze = new Maze(height, width);

        int startRow = 1;
        int startCol = 1;

        Cell currentCell = maze.getCell(startRow, startCol);
        currentCell.visitThisCell();
        Cell neighbourCell;
        Stack<Cell> cellStack = new Stack<>();

        while (true) {
            Cell randomNextCell = getRandomNeighbour(currentCell.getRow(), currentCell.getCol());

            if (randomNextCell != null) {
                randomNextCell.visitThisCell();
                destroyWall(currentCell, randomNextCell);

                cellStack.push(currentCell);
                currentCell = randomNextCell;
            } else if (!cellStack.isEmpty()) {
                currentCell = cellStack.pop();
            } else {
                break;
            }
        }

        return maze;
    }
}
