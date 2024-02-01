package edu.hw9.task1;

import java.util.concurrent.Callable;

public abstract class StatisticsHandler implements Callable<Double> {
    protected double[] data;

    public StatisticsHandler(double[] inputData) {
        this.data = inputData;
    }
}
