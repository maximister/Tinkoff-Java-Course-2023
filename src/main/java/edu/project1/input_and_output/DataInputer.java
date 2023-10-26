package edu.project1.input_and_output;

import java.util.Scanner;

public class DataInputer {
    private String inputData;
    private final Scanner scanner = new Scanner(System.in);

    public DataInputer() {
    }

    public String getInputData() {
        this.inputData = scanner.nextLine().toLowerCase();
        return inputData;
    }
}
