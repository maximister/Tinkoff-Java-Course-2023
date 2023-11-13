package edu.hw3.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static edu.hw3.task1.Encoder.encodeAtbash;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EncoderTest {
    @ParameterizedTest
    @DisplayName("Тестирование метода encodeAtbash")
    @MethodSource("encodeAtbashTestArgs")
    public void encodeAtbashTest(String input, String expected) {
        assertThat(encodeAtbash(input)).isEqualTo(expected);
    }

    private static Stream<Arguments> encodeAtbashTestArgs() {
        String longStringTest = "Any fool can write code that a computer can understand." +
            " Good programmers write code that humans can understand. ― Martin Fowler";
        String longStringResult = "Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw." +
            " Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi";

        return Stream.of(
            Arguments.of("Hello world!", "Svool dliow!"),
            Arguments.of(longStringTest, longStringResult),
            Arguments.of("12_*)", "12_*)"),
            Arguments.of("yrlvdplsh crush", "bioewkohs xifhs")
        );
    }

    @Test
    @DisplayName("null тест")
    public void encodeAtbushNullTest() {
        assertThrows(
            NullPointerException.class,
            () -> encodeAtbash(null)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidStringsSource")
    @DisplayName("non latin letters test")
    public void nonLatinLettersTest(String invalidString) {
        assertThrows(
            IllegalArgumentException.class,
            () -> encodeAtbash(invalidString)
        );
    }

    private static Stream<Arguments> invalidStringsSource() {

        return Stream.of(
            Arguments.of(
                "The letter с breaks tests to vile Jewish music!"),
                    Arguments.of(
                        "Заходит еврей в бар: — Кофе, пожалуйста. Бармен: IllegalArgumentException")
        );
    }
}
