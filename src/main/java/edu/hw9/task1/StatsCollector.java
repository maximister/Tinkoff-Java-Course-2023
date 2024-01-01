package edu.hw9.task1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StatsCollector {
    private final ConcurrentHashMap<String, List<Future<Double>>> metrics;
    private final ExecutorService executorService;
    private final static int THREADS_AMOUNT = 4;

    public StatsCollector() {
        metrics = new ConcurrentHashMap<>();
        executorService = Executors.newFixedThreadPool(THREADS_AMOUNT);
    }

    public void push(String metricName, double[] data) {
        Future<Double> sum = executorService.submit(new SumHandler(data));
        Future<Double> average = executorService.submit(new AverageHandler(data));
        Future<Double> maximum = executorService.submit(new MaximumHandler(data));
        Future<Double> minimum = executorService.submit(new MinimumHandler(data));
        metrics.put(metricName, new LinkedList<>(List.of(sum, average, maximum, minimum)));
    }

    public Map<String, List<Double>> stats() {
        Map<String, List<Double>> handledStats = new HashMap<>();
        for (var entry : metrics.entrySet()) {
            List<Double> calculatedData = new LinkedList<>();
            entry.getValue().forEach(future -> {
                try {
                    calculatedData.add(future.get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
            handledStats.put(entry.getKey(), calculatedData);
        }
        return handledStats;
    }
}
