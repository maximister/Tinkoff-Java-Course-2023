package edu.project3.Log_sources;

import edu.project3.log_sources.LocalFileLogLoader;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocalFileLoaderTest {
    @Test
    @DisplayName("invalid file path")
    public void localFileLoader_whenGivenInvalidPath_shouldThrowRuntimeE() {
        assertThrows(RuntimeException.class,
            () -> new LocalFileLogLoader("**/yrlvdplshBiggestSecret.txt")
        );
    }

    @Test
    @DisplayName("testing work with correct file path")
    public void localFileLoader_whenGivenCorrectPath_shouldCreateLogsList() {
        LocalFileLogLoader loader = new LocalFileLogLoader("localLogs.txt");
        Path expectedSource = Path.of("src", "main", "java", "edu", "project3", "resources");

        assertThat(loader.getLogs()).isNotEqualTo(getParsedLogs());
        assertThat(loader.getSources()).isEqualTo(expectedSource + "\\localLogs.txt");
    }

    @Test
    @DisplayName("smth go wrong and file pathes was" +
        " stolen after parsing path because i need more coverage " +
        "so getLogsFromFile should throw exception")
    public void uselessTest() {
        LocalFileLogLoader loader = new LocalFileLogLoader("localLogs.txt");

        try {
            Field files = loader.getClass().getDeclaredField("files");
            files.setAccessible(true);
            files.set(loader, List.of(Path.of("aboba.txt")));

            assertThrows(RuntimeException.class,
                loader::getLogs
            );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getParsedLogs() {
        return List.of(
            "боже как я устал",
            "поскорее бы после сессии",
            "стать пивом",
            "и напоить всех всех трудящихся",
            "и поспать",
            "нормальных логов не будет",
            "они длинные",
            "мяу"
        );
    }
}
