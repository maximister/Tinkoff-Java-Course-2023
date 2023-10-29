package edu.hw2.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import edu.hw2.task1.Expr.*;

public class ExprTest {
    @ParameterizedTest
    @CsvSource(value = {
        "4",
        "0",
        "-10",
        "12.3"
    })
    @DisplayName("Тест Const")
    public void constantTest(double value) {
        var res = new Constant(value);

        assertThat(res.evaluate()).isEqualTo(value);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "3.1",
        "12",
        "-4.1",
        "-3",
        "0"
    })
    @DisplayName("Тест Negate")
    public void negateTest(double value) {
        Negate res = new Negate(value);
        Negate resConst = new Negate(new Constant(value));

        assertThat(res.evaluate()).isEqualTo(-value);

        assertThat(resConst.evaluate()).isEqualTo(-value);
    }


    @ParameterizedTest
    @CsvSource(value = {
        "3.1, 2",
        "12, 3.1",
        "-4.1, -2",
        "-3, 2",
        "0, 1",
        "3, 0"
    })
    @DisplayName("Тест Exponent")
    public void exponentTest(double base, double exponent) {
        Exponent res = new Exponent(base, exponent);
        Exponent resConst = new Exponent(new Constant(base), new Constant(exponent));

        assertThat(res.evaluate()).isEqualTo(Math.pow(base, exponent));

        assertThat(resConst.evaluate()).isEqualTo(Math.pow(base, exponent));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "3.1, 2",
        "4, -4",
        "12, 3.1",
        "-4.1, -2",
        "-3, 2",
        "0, 1",
        "3, 0"
    })
    @DisplayName("Тест Addition")
    public void additionTest(double value1, double value2) {
        Addition res = new Addition(value1, value2);
        Addition resConst = new Addition(new Constant(value1), new Constant(value2));

        assertThat(res.evaluate()).isEqualTo(value1 + value2);
        assertThat(resConst.evaluate()).isEqualTo(value1 + value2);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "3.1, 2",
        "4, -4",
        "12, 3.1",
        "-4.1, -2.1",
        "-3, 2",
        "0, 1",
        "3, 0"
    })
    @DisplayName("Тест Multiplication")
    public void MultiplicationTest(double value1, double value2) {
        Multiplication res = new Multiplication(value1, value2);
        Multiplication resConst = new Multiplication(new Constant(value1), new Constant(value2));

        assertThat(res.evaluate()).isEqualTo(value1 * value2);
        assertThat(resConst.evaluate()).isEqualTo(value1 * value2);
    }

    @Test
    @DisplayName("Общий тест")
    public void commonTest() {
        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Constant(1));
        var sumTwoFour = new Addition(two, four);
        var mult = new Multiplication(sumTwoFour, negOne);
        var exp = new Exponent(mult, 2);
        var res = new Addition(exp, new Constant(1));

        assertThat(res.evaluate()).isEqualTo(37);
    }
}
