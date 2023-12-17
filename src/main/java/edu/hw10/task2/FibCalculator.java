package edu.hw10.task2;

public interface FibCalculator {
    @Cache(persist = false)
    long fib(int number);
}
