package edu.project1.input_and_output;

import org.jetbrains.annotations.NotNull;

public class Printer {
    public Printer() {
    }

    @SuppressWarnings("checkstyle:RegexpSinglelineJava")
    public void printMessage(@NotNull String message) {
        System.out.println("-> " + message);
    }
}
