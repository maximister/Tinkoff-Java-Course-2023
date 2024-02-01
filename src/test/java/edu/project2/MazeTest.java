package edu.project2;

import edu.project2.structures.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MazeTest {
    @Test
    @DisplayName("maze invalid size test")
    public void invalidSizeTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Maze(3, 3)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> new Maze(6, 5)
        );
    }

    @Test
    @DisplayName("get invalid cell test")
    public void invalidCellTest() {
        Maze maze = new Maze(5, 5);

        assertThrows(
            IllegalArgumentException.class,
            () -> maze.getCell(-1, 1)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> maze.getCell(1, 8)
        );
    }
}
