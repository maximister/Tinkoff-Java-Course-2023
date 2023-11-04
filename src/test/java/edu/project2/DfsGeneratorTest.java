package edu.project2;

import edu.project2.generator.DfsGenerator;
import edu.project2.renderer.ConsoleRenderer;
import edu.project2.renderer.Renderer;
import edu.project2.structures.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project2.MazeForTests.getRenderedTestMaze;
import static edu.project2.generator.DfsGenerator.getRandomForTests;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class DfsGeneratorTest {
    @Test
    @DisplayName("DFS algorithm for generating a maze test")
    public void dfsGeneratorTest() {
        DfsGenerator generator = new DfsGenerator();
        Renderer renderer = new ConsoleRenderer();
        getRandomForTests().setSeed(2);

        Maze maze = generator.generate(5, 5);

        assertThat(renderer.render(maze)).isEqualTo(getRenderedTestMaze());
    }
}
