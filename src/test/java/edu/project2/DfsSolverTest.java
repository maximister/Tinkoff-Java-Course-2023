package edu.project2;

import edu.project2.generator.DfsGenerator;
import edu.project2.renderer.ConsoleRenderer;
import edu.project2.renderer.Renderer;
import edu.project2.solver.DfsSolver;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static edu.project2.MazeForTests.getRenderedTestMAzeWithPath;
import static edu.project2.generator.DfsGenerator.getRandomForTests;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class DfsSolverTest {
    @Test
    @DisplayName("DFS algorithm for solving a maze test")
    public void dfsSolverTest() {
        DfsGenerator generator = new DfsGenerator();
        DfsSolver solver = new DfsSolver();
        Renderer renderer = new ConsoleRenderer();
        getRandomForTests().setSeed(2);

        Maze maze = generator.generate(5, 5);
        List<Coordinate> path = solver.solve(maze, new Coordinate(1, 1), new Coordinate(3, 3));

        assertThat(renderer.render(maze, path)).isEqualTo(getRenderedTestMAzeWithPath());
    }
}
