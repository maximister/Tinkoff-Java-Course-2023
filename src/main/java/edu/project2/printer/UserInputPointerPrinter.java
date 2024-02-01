package edu.project2.printer;

import edu.project2.ConsoleMessages;

public final class UserInputPointerPrinter implements Printer {

    public static final String YELLOW_BRIGHT = "\033[0;93m";
    public static final String RESET = "\033[0m";

    private UserInputPointerPrinter() {
    }

    @Override
    public void printMessage(String message) {
    }

    @Override
    public void printMaze(String renderResult) {
    }

    @SuppressWarnings("checkstyle:RegexpSinglelineJava")
    public static void userInputPointer() {
        System.out.print(YELLOW_BRIGHT + ConsoleMessages.USER_INPUT.getMessage() + RESET);
    }
}
