package edu.hw9.task1;

import java.util.Arrays;

public class AverageHandler extends StatisticsHandler {

    public AverageHandler(double[] inputData) {
        super(inputData);
    }

    @Override
    public Double call() {
        return Arrays.stream(data)
            .average()
            .orElseThrow();
    }
}
