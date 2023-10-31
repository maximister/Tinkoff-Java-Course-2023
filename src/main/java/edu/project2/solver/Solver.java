package edu.project2.solver;

import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import java.util.List;

/*
решение лабириннта
 */
//TODO: аналогично генерации
public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
