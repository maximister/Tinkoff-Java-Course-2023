package edu.project2;

import edu.project2.generator.DfsGenerator;
import edu.project2.generator.PrimsGenerator;
import edu.project2.input_reader.ConsoleInputHandler;
import edu.project2.solver.BfsSolver;
import edu.project2.solver.DfsSolver;
import edu.project2.structures.Cell;
import edu.project2.structures.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project2.MazeForTests.getTestMaze;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputHandlerTest {
    @Test
    @DisplayName("isExit test")
    public void isExitTest() {
        ConsoleInputHandler handler = new ConsoleInputHandler();

        assertThat(handler.isExit("EXIT")).isTrue();
        assertThat(handler.isExit("ExIt")).isTrue();
        assertThat(handler.isExit("exit")).isTrue();

        assertThat(handler.isExit("Dora")).isFalse();
    }

    @Test
    @DisplayName("parseSize test")
    public void parseSizeTest() {
        ConsoleInputHandler handler = new ConsoleInputHandler();
        String correctSize = "7x5";
        Integer[] parsedCorrectSize = {7, 5};

        String invalidSize = "3:7";
        String evenSize = "8x10";

        assertThat(handler.parseSize(correctSize)).isEqualTo(parsedCorrectSize);

        assertThrows(
            IllegalArgumentException.class,
            () -> handler.parseSize(invalidSize)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> handler.parseSize(evenSize)
        );
    }

    @Test
    @DisplayName("selectGenerator test")
    public void selectGeneratorTest() {
        ConsoleInputHandler handler = new ConsoleInputHandler();

        assertThat(handler.selectGenerator("1") instanceof DfsGenerator).isTrue();
        assertThat(handler.selectGenerator("2") instanceof PrimsGenerator).isTrue();

        assertThrows(IllegalArgumentException.class,
            () -> handler.selectGenerator("15"));
    }

    @Test
    @DisplayName("selectSolver test")
    public void selectSolverTest() {
        ConsoleInputHandler handler = new ConsoleInputHandler();

        assertThat(handler.selectSolver("1") instanceof DfsSolver).isTrue();
        assertThat(handler.selectSolver("2") instanceof  BfsSolver).isTrue();

        assertThrows(IllegalArgumentException.class,
            () -> handler.selectSolver("12"));
    }

    @Test
    @DisplayName("parseCoordinates test")
    public void parseCoordinatesTest() {
        ConsoleInputHandler handler = new ConsoleInputHandler();
        Cell[][] grid = getTestMaze().getGrid();

        String correctCoordinates = "1:1 3:3";
        Coordinate[] result = handler.parseCoordinates(correctCoordinates, 5, 5, grid);
        Coordinate[] expected = {new Coordinate(1, 1), new Coordinate(3, 3)};

        String invalidCoordinates = "1x1 5-5";
        String bigCoordinate = "100x100 200x200";
        String wallCoordinates = "1x1 2x2";

        assertThat(result[0].equals(expected[0]) && result[1].equals(expected[1])).isTrue();

        assertThrows(IllegalArgumentException.class,
            () -> handler.parseCoordinates(invalidCoordinates, 5, 5, grid));
        assertThrows(IllegalArgumentException.class,
            () -> handler.parseCoordinates(bigCoordinate, 5, 5, grid));
        assertThrows(IllegalArgumentException.class,
            () -> handler.parseCoordinates(wallCoordinates, 5, 5, grid));
    }


}
