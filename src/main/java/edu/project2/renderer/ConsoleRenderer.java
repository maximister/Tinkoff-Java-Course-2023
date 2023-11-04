package edu.project2.renderer;

import edu.project2.structures.Cell;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import java.util.Collections;
import java.util.List;

public class ConsoleRenderer implements Renderer {

    //TODO: использовать \t
    public static final String RED = "\033[0;31m";
    public static final String WHITE = "\u001B[37m";
    public static final String RESET = "\033[0m";
    public static final String PIXEL = "■  ";
    public static final String NO_PIXEL = "   ";

    @Override
    public String render(Maze maze) {
        return render(maze, Collections.emptyList());
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder renderedMaze = new StringBuilder();
        int width = maze.getWidth();
        int height = maze.getHeight();


        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (path.contains(new Coordinate(row, col))) {
                    renderedMaze.append(RED + PIXEL);
                } else if (maze.getCell(row, col).getType() == Cell.Type.WALL) {
                    renderedMaze.append(WHITE + PIXEL);
                } else {
                    renderedMaze.append(NO_PIXEL);
                }
            }
            renderedMaze.append('\n');
        }

        renderedMaze.append(RESET);

        return renderedMaze.toString();
    }
}
