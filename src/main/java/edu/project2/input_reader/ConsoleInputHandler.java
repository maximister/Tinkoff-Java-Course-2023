package edu.project2.input_reader;

import edu.project2.generator.DfsGenerator;
import edu.project2.generator.Generator;
import edu.project2.solver.DfsSolver;
import edu.project2.solver.Solver;
import edu.project2.structures.Coordinate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ConsoleInputHandler {
    private final static String COORDINATES_REGEX = "\\d:\\d \\d:\\d";
    private final static String SIZES_REGEX = "\\dx\\d";
    private final static String SIZES_EXCEPTION = "Неверные Размеры лабиринта!";
    private final static String COORDINATES_EXCEPTION =
        "Неверные координаты (возможно, в этой ячейке находится стена)!";
    private final static String INVALID_SELECTION_EXCEPTION = "Введено неверное значение!";
    private final static int MIN_SIZE = 5;

    public ConsoleInputHandler() {
    }

    public boolean isExit(String command) {
        return command.equalsIgnoreCase("EXIT");
    }

    public Integer[] parseSize(String size) {
        if (size == null || !Pattern.matches(SIZES_REGEX, size)) {
            throw new IllegalArgumentException(SIZES_EXCEPTION);
        }

        List<Integer> sizes = Arrays.stream(size.split("x")).map(Integer::parseInt).toList();
        if (sizes.get(0) < MIN_SIZE || sizes.get(1) < MIN_SIZE || sizes.get(0) % 2 == 0 || sizes.get(1) % 2 == 0) {
            throw new IllegalArgumentException(SIZES_EXCEPTION);
        }

        return sizes.toArray(Integer[]::new);
    }

    public Generator selectGenerator(String generator) {
        return switch (Integer.parseInt(generator)) {
            case 1 -> new DfsGenerator();
            //TODO: добавить
            case 2 -> null;
            default -> throw new IllegalArgumentException(INVALID_SELECTION_EXCEPTION);
        };
    }

    public Solver selectSolver(String generator) {
        return switch (Integer.parseInt(generator)) {
            case 1 -> new DfsSolver();
            //TODO: добавить
            case 2 -> null;
            default -> throw new IllegalArgumentException(INVALID_SELECTION_EXCEPTION);
        };
    }

    public Coordinate[] parseCoordinates(String coordinates, int width, int height) {
        if (coordinates == null || !Pattern.matches(COORDINATES_REGEX, coordinates)) {
            throw new IllegalArgumentException(COORDINATES_EXCEPTION);
        }

        List<Coordinate> splittedCoords = Arrays.stream(coordinates.split(" "))
            .map(ConsoleInputHandler::parseCoordinate).toList();

        if (!splittedCoords.get(0).isValidCoordinate(width, height)
            || !splittedCoords.get(1).isValidCoordinate(width, height)) {
            throw new IllegalArgumentException(COORDINATES_EXCEPTION);
        }

        return splittedCoords.toArray(new Coordinate[]{});
    }

    private static Coordinate parseCoordinate(String coordinate) {
        List<Integer> coords = Arrays.stream(coordinate.split(":")).map(Integer::parseInt).toList();

        return new Coordinate(coords.get(0), coords.get(1));
    }

}
