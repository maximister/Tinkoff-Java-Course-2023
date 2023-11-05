package edu.project2;

import edu.project2.generator.DfsGenerator;
import edu.project2.solver.BfsSolver;
import edu.project2.solver.DfsSolver;
import edu.project2.solver.Solver;
import edu.project2.structures.Cell;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static java.lang.Math.abs;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SolverTest {

    public boolean isPathCorrect(Maze maze, List<Coordinate> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            Cell cell1 = maze.getCell(path.get(i).row(), path.get(i).col());
            Cell cell2 = maze.getCell(path.get(i + 1).row(), path.get(i + 1).col());

            if (isWayExists(cell1, cell2)) {
                return false;
            }
        }

        return true;
    }

    private boolean isWayExists(Cell cell1, Cell cell2) {
        return (cell1.getType() == Cell.Type.PASSAGE
            && cell2.getType() == Cell.Type.PASSAGE
            && (abs(cell1.getRow() - cell2.getRow()) == 1)
            && (abs(cell1.getCol() - cell2.getCol()) == 1));
    }

    @Test
    @DisplayName("is DFS path correct")
    public void dfsSolverTest() {
        DfsGenerator generator = new DfsGenerator();
        Solver solver = new DfsSolver();

        Maze maze = generator.generate(11, 11);
        List<Coordinate> path = solver.solve(maze, new Coordinate(1, 1), new Coordinate(9, 9));

        assertThat(isPathCorrect(maze, path)).isTrue();
    }

    @Test
    @DisplayName("is BFS path correct")
    public void bfsSolverTest() {
        DfsGenerator generator = new DfsGenerator();
        Solver solver = new BfsSolver();

        Maze maze = generator.generate(11, 11);
        List<Coordinate> path = solver.solve(maze, new Coordinate(1, 1), new Coordinate(9, 9));

        assertThat(isPathCorrect(maze, path)).isTrue();
    }
}
