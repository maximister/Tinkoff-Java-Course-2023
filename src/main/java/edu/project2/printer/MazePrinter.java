package edu.project2.printer;


public class MazePrinter implements Printer {
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\033[0m";

    public MazePrinter() {
    }

    @Override
    @SuppressWarnings("checkstyle:RegexpSinglelineJava")
    public void printMessage(String message) {
        System.out.println(GREEN + "-> " + message + RESET);
    }

    @Override
    @SuppressWarnings("checkstyle:RegexpSinglelineJava")
    public void printMaze(String renderResult) {
        System.out.println(renderResult);
    }
}
