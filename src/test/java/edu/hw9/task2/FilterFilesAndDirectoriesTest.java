package edu.hw9.task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterFilesAndDirectoriesTest {
    @Test
    @DisplayName("Testing filtering directories by files amount")
    public void filterDirectoriesByFilesAmountTest() {
        Path testPath = Path.of("src/main/java/edu/project2");
        File testDirectory = testPath.toFile();
        List<File> expected = List.of(
            Path.of("src/main/java/edu/project2/generator").toFile(),
            Path.of("src/main/java/edu/project2/solver").toFile()
        );
        assertThat(    FileTasksHandler.filterDirectoriesByFilesAmount(testDirectory, 4)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Testing filtering files")
    public void filterFilesByPredicateTest() {
        Path testPath = Path.of("src/main/java/edu/project2");
        File testDirectory = testPath.toFile();
        Predicate<File> testPredicate =
            (file) -> {
                try {
                    return FilenameUtils.getExtension(file.toString()).equals("java")
                        && Files.size(file.toPath()) <= 180;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };
        List<File> expected = List.of(
            Path.of("src/main/java/edu/project2/generator/Generator.java").toFile(),
            Path.of("src/main/java/edu/project2/input_reader/InputReader.java").toFile(),
            Path.of("src/main/java/edu/project2/printer/Printer.java").toFile()
        );

        assertThat(FileTasksHandler.filterFilesByPredicate(testDirectory, testPredicate)).isEqualTo(expected);
    }
}
