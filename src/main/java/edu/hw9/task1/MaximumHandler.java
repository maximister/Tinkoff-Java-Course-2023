package edu.hw9.task1;

import java.util.Arrays;

public class MaximumHandler extends StatisticsHandler {
    public MaximumHandler(double[] inputData) {
        super(inputData);
    }

    @Override
    public Double call() {
        return Arrays.stream(data)
            .max()
            .orElseThrow();
    }
}
