package edu.project2;

import edu.project2.printer.MazePrinter;
import edu.project2.printer.Printer;
import edu.project2.renderer.ConsoleRenderer;
import edu.project2.renderer.Renderer;
import edu.project2.structures.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project2.MazeForTests.getRenderedTestMaze;
import static edu.project2.MazeForTests.getTestMaze;
import static edu.project2.printer.MazePrinter.GREEN;
import static edu.project2.printer.UserInputPointerPrinter.RESET;
import static edu.project2.printer.UserInputPointerPrinter.YELLOW_BRIGHT;
import static edu.project2.printer.UserInputPointerPrinter.userInputPointer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.io.*;

public class PrinterTest {
    @Test
    @DisplayName("Проверка метода printMaze")
    public void printMazeTest() {
        Maze testMaze = getTestMaze();
        Renderer renderer = new ConsoleRenderer();
        String renderedMaze = renderer.render(testMaze);
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        String expected = getRenderedTestMaze();

        try {
            System.setOut(new PrintStream(os, false, "UTF-8"));
            Printer printer = new MazePrinter();
            printer.printMaze(renderedMaze);

            String systemName = System.getProperty("os.name");
            if (systemName.contains("Windows")) {
                assertThat(os.toString("UTF-8")).isEqualTo(expected + "\r\n");
            } else {
                assertThat(os.toString("UTF-8")).isEqualTo(expected + "\n");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Проверка метода printMessage")
    public void printMessageTest() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        String expected = GREEN + "-> DoraDura" + RESET;

        try {
            System.setOut(new PrintStream(os, false, "UTF-8"));
            Printer printer = new MazePrinter();
            printer.printMessage("DoraDura");

            String systemName = System.getProperty("os.name");
            if (systemName.contains("Windows")) {
                assertThat(os.toString("UTF-8")).isEqualTo(expected + "\r\n");
            } else {
                assertThat(os.toString("UTF-8")).isEqualTo(expected + "\n");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Проверка метода userInputPointer")
    public void printPointerTest() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        String expected = YELLOW_BRIGHT + "-> " + RESET;

        try {
            System.setOut(new PrintStream(os, false, "UTF-8"));
            userInputPointer();

            assertThat(os.toString("UTF-8")).isEqualTo(expected);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
