package edu.hw11.task3;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw11.task3.FibonacciGenerator.generateFibonacciClass;
import static org.assertj.core.api.Assertions.assertThat;

public class BeteBuddyGeneratingClassTest {
    @SneakyThrows
    @Test
    @DisplayName("testing dynamically generated class for calculating fibonacci")
    public void generateFibonacciClass_shouldReturnCorrectFibonacciCounter() {
        //капец долго
        Class<?> dynamicallyGeneratedClass = generateFibonacciClass();
        long val = (long) dynamicallyGeneratedClass.getDeclaredMethod("count", int.class)
            .invoke(dynamicallyGeneratedClass.getDeclaredConstructor().newInstance(), 20);

        assertThat(val).isEqualTo(6765);
    }
}
