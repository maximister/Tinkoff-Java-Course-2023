package edu.project1;

import edu.project1.input_and_output.InputHandler;
import edu.project1.input_and_output.Printer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.*;


public class ConsoleHangmanTest {
    @Test
    @DisplayName("Ввод некорректного словаря")
    public void invalidDictionaryTest() {
        String[] words = new String[1];
        words[0] = "1";

        assertThrows(
            Exception.class,
            () -> new ConsoleHangman(5, words)
        );
    }

    @Test
    @DisplayName("Ввод некорректного количества попыток")
    public void invalidAttemptsTest() {
        String[] words = new String[1];
        words[0] = "correctword";

        assertThrows(
            IllegalArgumentException.class,
            () -> new ConsoleHangman(0, words)
        );
    }

    @Test
    @DisplayName("орректный запуск программы")
    public void runTest() {
        String[] dictionary = new String[1];
        dictionary[0] = "test";
        boolean exceptionFlag = false;

        try {
            System.setIn(new ByteArrayInputStream("t\ne\ns\n".getBytes()));
            new ConsoleHangman(5, dictionary);
        } catch (Exception e) {
            exceptionFlag = true;
        }

        assertThat(exceptionFlag).isTrue();
    }

}
