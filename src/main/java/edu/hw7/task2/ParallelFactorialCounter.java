package edu.hw7.task2;

import java.math.BigInteger;
import java.util.stream.Stream;

public final class ParallelFactorialCounter {
    private ParallelFactorialCounter() {
    }

    public static BigInteger getFactorial(long n) {
        if (n < 0) {
            throw new IllegalArgumentException("Impossible to get factorial of a negative number!");
        } else if (n == 0) {
            return BigInteger.ONE;
        }

        return Stream
            .iterate(BigInteger.ONE, i -> i.add(BigInteger.ONE))
            .limit(n)
            .parallel()
            .reduce(BigInteger::multiply)
            .orElseThrow();
    }
}
