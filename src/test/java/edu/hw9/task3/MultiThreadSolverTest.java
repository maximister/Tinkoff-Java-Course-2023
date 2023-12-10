package edu.hw9.task3;

import com.google.common.collect.Lists;
import edu.project2.generator.DfsGenerator;
import edu.project2.generator.Generator;
import edu.project2.renderer.ConsoleRenderer;
import edu.project2.renderer.Renderer;
import edu.project2.solver.DfsSolver;
import edu.project2.solver.Solver;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class MultiThreadSolverTest {
    @Test
    @DisplayName("testing multi thread solver")
    public void MultiThreadDfsSolverTest() {
        Generator generator = new DfsGenerator();
        Maze maze = generator.generate(51, 51);
        Renderer renderer = new ConsoleRenderer();
        Solver multiThreadSolver = new MultiThreadDfsSolver();
        Solver oneThreadSolver = new DfsSolver();

        List<Coordinate> oneThreadPath =
            oneThreadSolver.solve(maze, new Coordinate(1, 1), new Coordinate(49, 49));


        List<Coordinate> multiThreadPath =
            multiThreadSolver.solve(maze, new Coordinate(1, 1), new Coordinate(49, 49));
        //лабиринт идеальный => путь из начала в конец один =>
        //тк однопоточный solver был уже протестирован и работает корректно,
        // нам достаточно сравнить решение двух solver
        assertThat(multiThreadPath).isEqualTo(Lists.reverse(oneThreadPath));
    }
}
