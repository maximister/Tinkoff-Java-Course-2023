package edu.project2;

import edu.project2.generator.DfsGenerator;
import edu.project2.solver.BfsSolver;
import edu.project2.solver.Solver;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class DfsGeneratorTest {
    @Test
    @DisplayName("testing that DFS algorithm maze has solution")
    public void dfsGeneratorTest() {
        DfsGenerator generator = new DfsGenerator();
        Solver solver = new BfsSolver();
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(9, 9);
        Maze maze = generator.generate(11, 11);

        assertDoesNotThrow(() -> solver.solve(maze, start, end));
    }
}
