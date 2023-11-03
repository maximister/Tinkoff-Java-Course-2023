package edu.project2.input_reader;

import java.util.Scanner;

public class ConsoleReader implements InputReader {
    private final Scanner scanner;
    String buffer;

    public ConsoleReader() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String getInput() {
        buffer = scanner.nextLine();
        return buffer;
    }
}
