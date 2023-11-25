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
//P.S. я похоже овощ, и скорее всего проблема похоже была в том, что я забыл про то,
// что линух учитывает регистр в названии файлов, а винда - нет
//поэтому в тестах я с кайфом не проверял, насколько правильно я вообще переписал название файлика
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
        LocalFileLogLoader loader = new LocalFileLogLoader("fileForTest.txt");
        Path expectedSource = Path.of("src", "main", "java", "edu", "project3", "resources");
        String delimiter
            = System.getProperty("os.name").startsWith("Windows") ? "\\" : "/";

        assertThat(loader.getLogs()).isEqualTo(getParsedLogs());
        assertThat(loader.getSources()).isEqualTo(expectedSource + delimiter + "fileForTest.txt");
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
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
                "93.180.71.3 - - [17/May/2015:08:05:23 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
                "80.91.33.133 - - [17/May/2015:08:05:24 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\""
        );
    }
}
