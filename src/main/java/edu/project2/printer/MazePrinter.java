package edu.project2.printer;

import edu.project2.renderer.Renderer;

/*
можно использовать для вывода лабиринта
■ □
 */
public class MazePrinter implements Printer {

    MazePrinter() {
    }

    @Override
    @SuppressWarnings("checkstyle:RegexpSinglelineJava")
    public void printMessage(String message) {
        System.out.println("-> " + message);
    }

    @Override
    @SuppressWarnings("checkstyle:RegexpSinglelineJava")
    public void printMaze(Renderer renderResult) {
        System.out.println(renderResult);
    }
}
