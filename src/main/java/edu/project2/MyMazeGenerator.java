package edu.project2;

import edu.project2.generator.Generator;
import edu.project2.input_reader.ConsoleInputHandler;
import edu.project2.input_reader.ConsoleReader;
import edu.project2.input_reader.InputReader;
import edu.project2.printer.MazePrinter;
import edu.project2.printer.Printer;
import edu.project2.renderer.ConsoleRenderer;
import edu.project2.renderer.Renderer;
import edu.project2.solver.Solver;
import edu.project2.structures.Coordinate;
import edu.project2.structures.Maze;
import java.util.List;
import static edu.project2.generator.DfsGenerator.getRandomForTests;

public class MyMazeGenerator {
    private Generator mazeGenerator;
    private Solver mazeSolver;
    private final Renderer mazeRenderer;
    private final ConsoleInputHandler inputHandler;
    private final Printer printer;
    private final InputReader reader;
    private Maze maze;
    private List<Coordinate> path;

    public MyMazeGenerator() {
        printer = new MazePrinter();
        reader = new ConsoleReader();
        inputHandler = new ConsoleInputHandler();
        mazeRenderer = new ConsoleRenderer();
    }

    public void run() {
        printer.printMessage(ConsoleMessages.WELCOME_MESSAGE.getMessage());
        String command;
        Integer[] sizes;
        Coordinate[] coordinates;

        while (true) {
            printer.printMessage(ConsoleMessages.EXIT_COMMAND.getMessage());
            printer.printMessage(ConsoleMessages.SELECT_SIZE.getMessage());
            command = reader.getInput();

            if (inputHandler.isExit(command)) {
                printer.printMessage(ConsoleMessages.END_MESSAGE.getMessage());
                break;
            }

            sizes = inputHandler.parseSize(command);

            //generating
            printer.printMessage(ConsoleMessages.SELECT_GENERATOR.getMessage());
            command = reader.getInput();
            mazeGenerator = inputHandler.selectGenerator(command);
            maze = mazeGenerator.generate(sizes[0], sizes[1]);

            //printing generated maze
            printer.printMessage(ConsoleMessages.GENERATED_MAZE_MESSAGE.getMessage());
            printer.printMaze(mazeRenderer.render(maze));

            //getting start and end Cells
            printer.printMessage(ConsoleMessages.SELECT_COORDINATES.getMessage());
            command = reader.getInput();
            coordinates = inputHandler.parseCoordinates(command, sizes[0], sizes[1], maze.getGrid());

            //solving maze
            printer.printMessage(ConsoleMessages.SELECT_SOLVER.getMessage());
            command = reader.getInput();
            mazeSolver = inputHandler.selectSolver(command);
            path = mazeSolver.solve(maze, coordinates[0], coordinates[1]);

            //printing solved maze
            printer.printMessage(ConsoleMessages.SOLVED_MAZE_MESSAGE.getMessage());
            printer.printMaze(mazeRenderer.render(maze, path));
        }
    }

    public void runForTest() {
       getRandomForTests().setSeed(2);
       run();
    }

    /*
    public static void main(String[] args) {
        MyMazeGenerator myMaze = new MyMazeGenerator();
        myMaze.run();
    }
     */
}
