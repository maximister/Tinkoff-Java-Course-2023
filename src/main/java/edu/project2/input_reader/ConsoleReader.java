package edu.project2.input_reader;

import java.util.Scanner;

public class ConsoleReader implements InputReader {
    private final Scanner scanner;
    String buffer;

    ConsoleReader() {
        scanner = new Scanner(System.in);
    }

    @Override public void readInput() {
        buffer = scanner.nextLine();
    }

    @Override
    public String getInput() {
        return buffer;
    }
}
