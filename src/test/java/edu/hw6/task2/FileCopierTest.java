package edu.hw6.task2;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;
import static edu.hw6.task2.FileCopier.cloneFile;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileCopierTest {
    @ParameterizedTest
    @MethodSource("getFileName")
    @DisplayName("cloneFile test")
    public void cloneFileTest(Path filePath) {
        makeFile(filePath);
        Path copyPath = cloneFile(filePath);

        assertThat(copyPath.getFileName().toString())
            .isEqualTo(FilenameUtils.getBaseName(filePath.toString()) + " — копия.txt");
        assertThat(getFileContent(filePath)).isEqualTo(getFileContent(copyPath));

        copyPath = cloneFile(filePath);
        copyPath = cloneFile(filePath);

        assertThat(copyPath.getFileName().toString())
            .isEqualTo(FilenameUtils.getBaseName(filePath.toString()) + " — копия (2).txt");
        assertThat(getFileContent(filePath)).isEqualTo(getFileContent(copyPath));

        copyPath.getParent().resolve("test — копия.txt").toFile().delete();
        copyPath.getParent().resolve("test — копия (1).txt").toFile().delete();
        copyPath.toFile().delete();
        filePath.toFile().delete();
    }

    @ParameterizedTest
    @MethodSource("getFileName")
    @DisplayName("coping no existing file")
    public void noExistingFileCopyTest(Path filePath) {

        assertThrows(
            FileSystemNotFoundException.class,
            () -> {
                cloneFile(filePath);
            }
        );
    }

    private static Stream<Arguments> getFileName() {
        String currentAbsolutePathString = Paths.get("").toAbsolutePath().toString();
        Path path = Paths.get(currentAbsolutePathString, "test.txt");
        return Stream.of(
            Arguments.of(path)
        );
    }

    public String getFileContent(Path file) {
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder data = new StringBuilder();
            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine());
            }
            return data.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeFile(Path path) {
        if (!path.toFile().exists()) {
            File file = path.toFile();
            try (FileWriter writer = new FileWriter(path.toString())) {
                writer.write("какой-то текст для текста\nхочу питсу");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
