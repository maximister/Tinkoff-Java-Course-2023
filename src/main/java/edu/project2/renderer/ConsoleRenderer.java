package edu.project2.renderer;

import edu.project2.generator.DfsGenerator;
import edu.project2.solver.DfsSolver;
import edu.project2.structures.Cell;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import java.util.Collections;
import java.util.List;

public class ConsoleRenderer implements Renderer {

    public static final String RED = "\033[0;31m";
    public static final String WHITE = "\u001B[37m";
    public static final String RESET = "\033[0m";

    @Override
    public String render(Maze maze) {
        return render(maze, Collections.emptyList());
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder renderedMaze = new StringBuilder();
        int width = maze.getWidth();
        int height = maze.getHeight();


        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                if (path.contains(new Coordinate(row, col))) {
                    renderedMaze.append(RED + "■  ");
                } else if (maze.getCell(row, col).getType() == Cell.Type.WALL) {
                    renderedMaze.append(WHITE + "■  ");
                } else {
                    renderedMaze.append("   ");
                }
            }
            renderedMaze.append('\n');
        }

        renderedMaze.append(RESET);

        return renderedMaze.toString();
    }

    public static void main(String[] args) {
        DfsGenerator g = new DfsGenerator();
        Maze maze = g.generate(11, 11);
        DfsSolver s = new DfsSolver();
        ConsoleRenderer r = new ConsoleRenderer();
        System.out.println(r.render(maze));
        Coordinate start = new Coordinate(1,1);
        Coordinate end = new Coordinate(9, 9);
        List<Coordinate> path = s.solve(maze, start, end);
        System.out.println(r.render(maze, path));
    }
}
