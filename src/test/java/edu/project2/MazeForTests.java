package edu.project2;

import edu.project2.structures.Cell;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import java.util.ArrayList;
import java.util.List;
import static edu.project2.printer.UserInputPointerPrinter.RESET;
import static edu.project2.renderer.ConsoleRenderer.RED;
import static edu.project2.renderer.ConsoleRenderer.WHITE;

public class MazeForTests {
    private MazeForTests() {
    }

    public static Maze getTestMaze() {
        Maze testMaze = new Maze(5, 5);

        int[][] mazeMatrix = {
            {1, 1, 1, 1, 1},
            {1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1},
        };

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                setCell(i, j, testMaze, mazeMatrix[i][j]);
            }
        }

        return testMaze;
    }

    private static void setCell(int row, int col, Maze maze, int type) {
        switch (type) {
            case 1:
                maze.setCell(row, col, Cell.Type.WALL);
                break;
            case 0:
                maze.setCell(row, col, Cell.Type.PASSAGE);
                break;
        }
    }

    public static List<Coordinate> getTestPath() {
        List<Coordinate> path = new ArrayList<>();
        path.add(new Coordinate(1, 1));
        path.add(new Coordinate(2, 1));
        path.add(new Coordinate(3, 1));
        path.add(new Coordinate(3, 2));
        path.add(new Coordinate(3, 3));

        return path;
    }

    public static String getRenderedTestMaze() {
        return WHITE + "■  " + WHITE + "■  " + WHITE + "■  " + WHITE + "■  " + WHITE + "■  \n"
            + WHITE + "■  "  + "   " +  WHITE + "■  " + "   " +  WHITE + "■  \n"
            + WHITE + "■  "  + "   " +  WHITE + "■  "  + "   " +  WHITE + "■  \n"
            + WHITE + "■  "  + "         "  +  WHITE + "■  \n"
            + WHITE + "■  " + WHITE + "■  " + WHITE + "■  " + WHITE + "■  " + WHITE + "■  \n"  + RESET;
    }

    public static String getRenderedTestMAzeWithPath() {
        return WHITE + "■  " + WHITE + "■  " + WHITE + "■  " + WHITE + "■  " + WHITE + "■  \n"
            + WHITE + "■  "  + RED + "■  " +  WHITE + "■  " + "   " +  WHITE + "■  \n"
            + WHITE + "■  "  + RED + "■  " +  WHITE + "■  "  + "   " +  WHITE + "■  \n"
            + WHITE + "■  "  + RED + "■  " + RED + "■  " + RED + "■  " +  WHITE + "■  \n"
            + WHITE + "■  " + WHITE + "■  " + WHITE + "■  " + WHITE + "■  " + WHITE + "■  \n"  + RESET;
    }
}
