package edu.project2;

import edu.project2.generator.PrimsGenerator;
import edu.project2.solver.BfsSolver;
import edu.project2.solver.Solver;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PrimsGeneratorTest {
    @Test
    @DisplayName("Testing that Prims algorithm has solution")
    public void dfsGeneratorTest() {
        PrimsGenerator generator = new PrimsGenerator();
        Solver solver = new BfsSolver();
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(9, 9);
        Maze maze = generator.generate(11, 11);

        assertDoesNotThrow(() -> solver.solve(maze, start, end));
    }
}
