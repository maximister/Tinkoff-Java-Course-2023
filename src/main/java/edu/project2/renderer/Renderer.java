package edu.project2.renderer;

import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import java.util.List;

/*
обработка лабиринта для вывода
можно сделать разные стили вывода
 */
public interface Renderer {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}

