package edu.project2.generator;

import edu.project2.structures.Cell;
import edu.project2.structures.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class DFSGenerator implements Generator {
    private Maze maze;
    private static final int[][] MOVES_TO_NEIGHBOURS = {{0, -2}, {0, 2}, {-2, 0}, {2, 0}};
    private static final Random RANDOM = new Random();

    @Override
    public Maze generate(int height, int width) {
        maze = new Maze(height, width);

        //TODO: Возможно следует добавить возможность пользовательского ввода
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

    private Cell getRandomNeighbour(int row, int col) {
        List<Cell> neighbours = new ArrayList<>();

        for (int[] move : Arrays.asList(MOVES_TO_NEIGHBOURS)) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            if (maze.isValidCell(newRow, newCol) && !maze.getCell(newRow, newCol).isVisited()) {
                Cell neighbour = maze.getCell(newRow, newCol);
                neighbours.add(neighbour);
            }
        }

        if (!neighbours.isEmpty()) {
            return neighbours.get(RANDOM.nextInt(neighbours.size()));
        }

        return null;
    }

    private void destroyWall(Cell firstCell, Cell secondCell) {
        int wallRow = (firstCell.getRow() + secondCell.getRow()) / 2;
        int wallCol = (firstCell.getCol() + secondCell.getCol()) / 2;

        maze.setCell(wallRow, wallCol, Cell.Type.PASSAGE);
        Cell destroyedWall = maze.getCell(wallRow, wallCol);
        destroyedWall.visitThisCell();
    }

    public static void main(String[] args) {
        DFSGenerator g = new DFSGenerator();
        Maze myMaze = g.generate(21, 21);
        myMaze.testPrint();
    }
}
