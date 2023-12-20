package edu.hw7.task4;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MonteCarloPiComputer {
    private static final double MONTE_CARLO_CONST = 4;
    private static final double RADIUS = 0.5;

    public MonteCarloPiComputer() {
    }

    public double computePiInOneThread(long iterations) {
        double circleCount = countPointsInCircle(iterations);
        return MONTE_CARLO_CONST * (circleCount / iterations);
    }

    private long countPointsInCircle(long iterations) {
        long circleCount = 0;
        Random random = ThreadLocalRandom.current();

        for (int i = 0; i < iterations; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();

            if (isInCircle(x - RADIUS, y - RADIUS)) {
                circleCount++;
            }
        }

        return circleCount;
    }

    public double computePiMultiThreading(long iterations, int threadsAmount) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(threadsAmount)) {
            Future<Long>[] futures = new Future[threadsAmount];

            for (int i = 0; i < threadsAmount - 1; i++) {
                futures[i] = executorService.submit(() -> {
                    return countPointsInCircle(iterations / threadsAmount);
                });
            }

            futures[threadsAmount - 1] = executorService.submit(() -> {
                return countPointsInCircle(iterations / threadsAmount + iterations % threadsAmount);
            });

            double circleCount = 0;
            for (var future : futures) {
                circleCount += future.get();
            }

            executorService.shutdown();
            if (!executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)) {
                throw new RuntimeException();
            }
            return MONTE_CARLO_CONST * (circleCount / iterations);

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isInCircle(double x, double y) {
        return x * x + y * y <= RADIUS * RADIUS;
    }
}
