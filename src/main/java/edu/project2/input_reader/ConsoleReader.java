package edu.project2.input_reader;

import java.util.Scanner;
import static edu.project2.printer.UserInputPointerPrinter.userInputPointer;

public class ConsoleReader implements InputReader {
    private final Scanner scanner;
    String buffer;

    public ConsoleReader() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String getInput() {
        userInputPointer();
        buffer = scanner.nextLine();
        return buffer;
    }
}
