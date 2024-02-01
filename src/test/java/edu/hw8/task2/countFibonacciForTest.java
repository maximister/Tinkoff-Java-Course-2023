package edu.hw8.task2;

public class countFibonacciForTest {
    public static int count(int n) {
        if (n == 1 || n == 0) {
            return n;
        }
        return count(n - 1) + count(n - 2);
    }
}
