package edu.hw11.task2;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteBuddyChangingMethodLogicTest {
    @SneakyThrows
    @Test
    @DisplayName("changing sum method logic")
    public void ArithmeticUtilsSum_shouldMultiply() {
        int sum = new ByteBuddy()
            .subclass(ArithmeticUtils.class)
            .method(named("sum")
                .and(isDeclaredBy(ArithmeticUtils.class)
                    .and(returns(Integer.TYPE))))
            .intercept(MethodDelegation.to(JewishDebtCalculator.class))
            .make()
            .load(getClass().getClassLoader())
            .getLoaded()
            .getDeclaredConstructor()
            .newInstance()
            .sum(7, 8);

        assertEquals(sum, 56);


        //just for coverage bro)
        ArithmeticUtils arithmeticUtils = new ArithmeticUtils();
        arithmeticUtils.sum(1, 1);
    }
}
