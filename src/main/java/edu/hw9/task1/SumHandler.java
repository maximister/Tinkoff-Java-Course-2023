package edu.hw9.task1;

import java.util.Arrays;

public class SumHandler extends StatisticsHandler {
    public SumHandler(double[] inputData) {
        super(inputData);
    }

    @Override
    public Double call() {
        return Arrays.stream(data)
            .sum();
    }
}
