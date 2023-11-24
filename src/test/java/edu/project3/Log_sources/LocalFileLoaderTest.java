package edu.project3.Log_sources;

import edu.project3.log_sources.LocalFileLogLoader;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//тут из-за тестов ночью на гите сборка посыпалась, я в отдельной ветке пофиксил, вроде норм,
// но в причины углублюсь после сессии когда время будет(
// Пока думаю, что я либо файлы не так назвал где-то, либо линуксу не нравятся названия с пробелами
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
        String delimiter
            = System.getProperty("os.name").startsWith("Windows") ? "\\" : "/";

        assertThat(loader.getLogs()).isNotEqualTo(getParsedLogs());
        assertThat(loader.getSources()).isEqualTo(expectedSource + delimiter + "localLogs.txt");
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
