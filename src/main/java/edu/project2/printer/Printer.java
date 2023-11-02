package edu.project2.printer;

import edu.project2.renderer.Renderer;

public interface Printer {
    void printMessage(String message);

    //TODO: заменить аргумент на результат работы рендера
    void printMaze(Renderer renderResult);
}
