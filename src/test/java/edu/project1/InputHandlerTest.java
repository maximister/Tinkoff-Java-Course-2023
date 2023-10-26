package edu.project1;

import edu.project1.input_and_output.InputHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.*;

public class InputHandlerTest {
    @Test
    @DisplayName("Ввод пустой строки")
    public void emptyInputTest() {
        System.setIn(new ByteArrayInputStream("\n".getBytes()));

        InputHandler inputHandler = new InputHandler();
        assertThrows(
            IllegalArgumentException.class,
            () -> inputHandler.next()
        );
    }

    @Test
    @DisplayName("Ввод небуквенного значения")
    public void notALetterTest() {
        System.setIn(new ByteArrayInputStream("*".getBytes()));

        InputHandler inputHandler = new InputHandler();
        assertThrows(
            IllegalArgumentException.class,
            () -> inputHandler.next()
        );
    }

    @Test
    @DisplayName("Неверная команда")
    public void invalidCommandTest() {
        String message = "Error: Invalid command! Enter a letter or the command \"exit\"!";
        InputHandler inputHandler = new InputHandler();

        System.setIn(new ByteArrayInputStream("stop".getBytes()));

        assertThat(inputHandler.next()).isEqualTo(message);
    }

    @ParameterizedTest
    @MethodSource("correctInputTestArgs")
    @DisplayName("Корректный ввод")
    public void correctInputTest(String input, String result) {
        InputHandler inputHandler = new InputHandler();
        String command = input;

        System.setIn(new ByteArrayInputStream(command.getBytes()));

        assertThat(inputHandler.next()).isEqualTo(result);
    }

    private static Stream<Arguments> correctInputTestArgs() {
        return Stream.of(
            Arguments.of("exit", "exit"),
            Arguments.of("Exit", "exit"),
            Arguments.of("EXIT", "exit"),
            Arguments.of("a", "a"),
            Arguments.of("A", "a")
        );
    }

}
