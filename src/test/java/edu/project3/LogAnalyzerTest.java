package edu.project3;

import edu.project3.log_sources.LocalFileLogLoader;
import edu.project3.log_sources.LogsSource;
import edu.project3.log_sources.UrlLogLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogAnalyzerTest {
    @Test
    @DisplayName("correct input commands test")
    public void logAnalyzer_shouldNotThrowsExceptions() {
        String command = "java -jar nginx-log-stats.jar --path *  --format adoc";
        assertDoesNotThrow(() -> new LogAnalyzer(command));
    }

    @ParameterizedTest
    @MethodSource("getInvalidCommands")
    @DisplayName("invalid input commands test")
    public void logAnalyzer_shouldThrowsExceptions(String command, Exception exception) {
        assertThrows(
            exception.getClass(),
            () -> new LogAnalyzer(command)
        );
    }

    private static Stream<Arguments> getInvalidCommands() {
        return Stream.of(
            Arguments.of(
                "java -jar nginx-log-stats.jar --path welcomeToTheClub  --format adoc",
                new RuntimeException()
            ),
            Arguments.of("java -jar nginx-log-stats.jar --path *  --format abob", new RuntimeException()),
            Arguments.of("make tea stupid program", new RuntimeException())
        );
    }
}
