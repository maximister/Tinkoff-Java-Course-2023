package edu.hw9.task1;

import java.util.Arrays;

public class MinimumHandler extends StatisticsHandler {
    public MinimumHandler(double[] inputData) {
        super(inputData);
    }

    @Override
    public Double call() {
        return Arrays.stream(data)
            .min()
            .orElseThrow();
    }
}
