package edu.project2.generator;

import edu.project2.structures.Cell;
import edu.project2.structures.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * <pre>
 * Генерация лабиринта методом поиска в глубину
 * Источник: https://habr.com/ru/articles/262345/
 * </pre>
 */
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
