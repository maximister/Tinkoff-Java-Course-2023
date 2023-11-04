package edu.project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import static edu.project2.MazeForTests.getRenderedTestMAzeWithPath;
import static edu.project2.MazeForTests.getRenderedTestMaze;
import static edu.project2.printer.MazePrinter.GREEN;
import static edu.project2.printer.MazePrinter.RESET;
import static edu.project2.printer.UserInputPointerPrinter.YELLOW_BRIGHT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//TODO: вынести вывод в отдельные методы
public class MyMazeGeneratorTest {
    @Test
    @DisplayName("exit command test")
    public void exitCommandTest() {
        System.setIn(new ByteArrayInputStream("exit".getBytes()));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        MyMazeGenerator myMazeGenerator = new MyMazeGenerator();

        String expectedWindows = GREEN + "-> " + ConsoleMessages.WELCOME_MESSAGE.getMessage() + RESET + "\r\n"
            + GREEN + "-> " + ConsoleMessages.EXIT_COMMAND.getMessage() + RESET + "\r\n"
            + GREEN + "-> " + ConsoleMessages.SELECT_SIZE.getMessage() + RESET + "\r\n"
            + YELLOW_BRIGHT + "-> " + RESET
            + GREEN + "-> " + ConsoleMessages.END_MESSAGE.getMessage() + RESET;

        String expectedUnix = GREEN + "-> " + ConsoleMessages.WELCOME_MESSAGE.getMessage() + RESET + "\n"
            + GREEN + "-> " + ConsoleMessages.EXIT_COMMAND.getMessage() + RESET + "\n"
            + GREEN + "-> " + ConsoleMessages.SELECT_SIZE.getMessage() + RESET + "\n"
            + YELLOW_BRIGHT + "-> " + RESET
            + GREEN + "-> " + ConsoleMessages.END_MESSAGE.getMessage() + RESET;

        try {
            System.setOut(new PrintStream(os, false, "UTF-8"));
            myMazeGenerator.run();
            String systemName = System.getProperty("os.name");
            if (systemName.contains("Windows")) {
                assertThat(os.toString("UTF-8")).isEqualTo(expectedWindows + "\r\n");
            } else {
                assertThat(os.toString("UTF-8")).isEqualTo(expectedUnix + "\n");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("run test")
    public void runTest() {
        System.setIn(new ByteArrayInputStream("5x5\n1\n1:1 3:3\n1\nexit".getBytes()));
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        MyMazeGenerator myMazeGenerator = new MyMazeGenerator();

        String expectedWindows = GREEN + "-> " + ConsoleMessages.WELCOME_MESSAGE.getMessage() + RESET + "\r\n"
            + GREEN + "-> " + ConsoleMessages.EXIT_COMMAND.getMessage() + RESET + "\r\n"
            + GREEN + "-> " + ConsoleMessages.SELECT_SIZE.getMessage() + RESET + "\r\n"
            + YELLOW_BRIGHT + "-> " + RESET
            + GREEN + "-> " + ConsoleMessages.SELECT_GENERATOR.getMessage() + RESET + "\r\n"
            + YELLOW_BRIGHT + "-> " + RESET
            + GREEN + "-> " + ConsoleMessages.GENERATED_MAZE_MESSAGE.getMessage() + RESET + "\r\n"
            + getRenderedTestMaze() + "\r\n"
            + GREEN + "-> " + ConsoleMessages.SELECT_COORDINATES.getMessage() + RESET + "\r\n"
            + YELLOW_BRIGHT + "-> " + RESET
            + GREEN + "-> " + ConsoleMessages.SELECT_SOLVER.getMessage() + RESET + "\r\n"
            + YELLOW_BRIGHT + "-> " + RESET
            + GREEN + "-> " + ConsoleMessages.SOLVED_MAZE_MESSAGE.getMessage() + RESET + "\r\n"
            + getRenderedTestMAzeWithPath() + "\r\n"
            + GREEN + "-> " + ConsoleMessages.EXIT_COMMAND.getMessage() + RESET + "\r\n"
            + GREEN + "-> " + ConsoleMessages.SELECT_SIZE.getMessage() + RESET + "\r\n"
            + YELLOW_BRIGHT + "-> " + RESET
            + GREEN + "-> " + ConsoleMessages.END_MESSAGE.getMessage() + RESET;

        String expectedUnix = GREEN + "-> " + ConsoleMessages.WELCOME_MESSAGE.getMessage() + RESET + "\n"
            + GREEN + "-> " + ConsoleMessages.EXIT_COMMAND.getMessage() + RESET + "\n"
            + GREEN + "-> " + ConsoleMessages.SELECT_SIZE.getMessage() + RESET + "\n"
            + YELLOW_BRIGHT + "-> " + RESET
            + GREEN + "-> " + ConsoleMessages.SELECT_GENERATOR.getMessage() + RESET + "\n"
            + YELLOW_BRIGHT + "-> " + RESET
            + GREEN + "-> " + ConsoleMessages.GENERATED_MAZE_MESSAGE.getMessage() + RESET + "\n"
            + getRenderedTestMaze() + "\n"
            + GREEN + "-> " + ConsoleMessages.SELECT_COORDINATES.getMessage() + RESET + "\n"
            + YELLOW_BRIGHT + "-> " + RESET
            + GREEN + "-> " + ConsoleMessages.SELECT_SOLVER.getMessage() + RESET + "\n"
            + YELLOW_BRIGHT + "-> " + RESET
            + GREEN + "-> " + ConsoleMessages.SOLVED_MAZE_MESSAGE.getMessage() + RESET + "\n"
            + getRenderedTestMAzeWithPath() + "\n"
            + GREEN + "-> " + ConsoleMessages.EXIT_COMMAND.getMessage() + RESET + "\n"
            + GREEN + "-> " + ConsoleMessages.SELECT_SIZE.getMessage() + RESET + "\n"
            + YELLOW_BRIGHT + "-> " + RESET
            + GREEN + "-> " + ConsoleMessages.END_MESSAGE.getMessage() + RESET;

        try {
            System.setOut(new PrintStream(os, false, "UTF-8"));
            myMazeGenerator.runForTest();
            String systemName = System.getProperty("os.name");
            if (systemName.contains("Windows")) {
                assertThat(os.toString("UTF-8")).isEqualTo(expectedWindows + "\r\n");
            } else {
                assertThat(os.toString("UTF-8")).isEqualTo(expectedUnix + "\n");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
