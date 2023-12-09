package edu.project2;

import edu.project2.renderer.ConsoleRenderer;
import edu.project2.renderer.Renderer;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project2.MazeForTests.getRenderedTestMAzeWithPath;
import static edu.project2.MazeForTests.getRenderedTestMaze;
import static edu.project2.MazeForTests.getTestMaze;
import static edu.project2.MazeForTests.getTestPath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.List;

public class RendererTest {

    @Test
    @DisplayName("Maze without path test")
    public void renderMazeWithoutPathTest() {
        Maze testMaze = getTestMaze();
        Renderer renderer = new ConsoleRenderer();

        String renderedMaze = renderer.render(testMaze);

        String WHITE = "\u001B[37m";
        String RESET = "\033[0m";

        String expected = getRenderedTestMaze();
        assertThat(renderedMaze).isEqualTo(expected);
    }

    @Test
    @DisplayName("Maze with a path")
    public void renderMazeWithPathTest() {
        Maze testMaze = getTestMaze();
        List<Coordinate> path = getTestPath();
        Renderer renderer = new ConsoleRenderer();

        String renderedMaze = renderer.render(testMaze, path);

        String expected = getRenderedTestMAzeWithPath();

        assertThat(renderedMaze).isEqualTo(expected);
    }
}
