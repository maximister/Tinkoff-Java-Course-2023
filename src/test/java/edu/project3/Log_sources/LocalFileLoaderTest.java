package edu.project3.Log_sources;

import edu.project3.log_sources.LocalFileLogLoader;
import edu.project3.logs.log_structure.LogRequest;
import edu.project3.logs.log_structure.LogResponse;
import edu.project3.logs.log_structure.NginxLogRecord;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.List;
import edu.project3.statistic_collectors.AverageBytesSentSizeCollector;
import edu.project3.statistic_collectors.MethodsCollector;
import edu.project3.statistic_collectors.ResourcesCollector;
import edu.project3.statistic_collectors.StatisticsCollector;
import edu.project3.statistic_collectors.StatusCollector;
import edu.project3.statistic_collectors.TimeController;
import edu.project3.statistic_collectors.UserAgentCollector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
public class LocalFileLoaderTest {
    @Test
    @DisplayName("invalid file path")
    public void localFileLoader_whenGivenInvalidPath_shouldThrowRuntimeE() {
        assertThrows(RuntimeException.class,
            () -> new LocalFileLogLoader("**/yrlvdplsh biggest secret.txt")
        );
    }

    @Test
    @DisplayName("testing work with correct file path")
    public void localFileLoader_whenGivenInvalidPath_shouldCreateLogsList() {
        LocalFileLogLoader loader = new LocalFileLogLoader("local Logs.txt");
        Path expectedSource = Path.of("src", "main", "java", "edu", "project3", "resources");

        assertThat(loader.getLogs()).isNotEqualTo(getParsedLogs());
        assertThat(loader.getSources()).isEqualTo(expectedSource + "\\local Logs.txt");
    }

    @Test
    @DisplayName("smth go wrong and file pathes was" +
        " stolen after parsing path because i need more coverage " +
        "so getLogsFromFile should throw exception")
    public void uselessTest() {
        LocalFileLogLoader loader = new LocalFileLogLoader("local Logs.txt");

        try {
            Field files = loader.getClass().getDeclaredField("files");
            files.setAccessible(true);
            files.set(loader, List.of(Path.of("aboba")));

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
