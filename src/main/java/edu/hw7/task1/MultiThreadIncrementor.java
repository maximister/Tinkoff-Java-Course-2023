package edu.hw7.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadIncrementor {
    private final int threadsAmount;
    private final AtomicInteger counter;

    public MultiThreadIncrementor(int threadsAmount) {
        this.threadsAmount = threadsAmount;
        counter = new AtomicInteger();
    }

    public void add(int value) {
        List<Thread> threadsList = new ArrayList<>();

        threadsList.add(getIncrementorThread(value / threadsAmount + value % threadsAmount));

        for (int i = 0; i < threadsAmount - 1; i++) {
            threadsList.add(getIncrementorThread(value / threadsAmount));
        }

        for (int i = 0; i < threadsAmount; i++) {
            threadsList.get(i).start();
            try {
                threadsList.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Thread getIncrementorThread(int iterations) {
        return new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                counter.incrementAndGet();
            }
        });
    }

    public int getCounter() {
        return counter.get();
    }
}
