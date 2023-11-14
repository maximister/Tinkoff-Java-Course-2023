package edu.hw6.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static edu.hw6.task4.StreamComposition.compose;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*Дружку-пирожку семинарист посоветовал называть тесты примерно в таком формате:
    ИмяМетода_should что проверяем
    Попробовал тут так, стало лучше?*/
public class StreamCompositionTest {

    @Test
    @DisplayName("creation file test")
    public void streamComposition_shouldCreateFile() {
        compose();

        assertTrue(Files.exists(Path.of("bimbim.txt")));

        Path.of("bimbim.txt").toFile().delete();
    }

    @Test
    @DisplayName("file data and message is the same test")
    public void streamComposition_shouldWriteCorrectData() throws IOException {
        compose();

        String expectedData = "Programming is learned by writing programs. ― Brian Kernighan\n"
            + "PYTHON ETO EASY BIM BIM BAM BAM. ― python_is_trash";
        String fileData = getFileData("bimbim.txt");

        assertEquals(fileData, expectedData);

        Path.of("bimbim.txt").toFile().delete();
    }

    private String getFileData(String file) throws IOException {
        List<String> fileDataList = Files.readAllLines(Path.of(file));
        StringBuilder fileData = new StringBuilder();
        for (int i = 0; i < fileDataList.size() - 1; i++) {
            fileData.append(fileDataList.get(i));
            fileData.append('\n');
        }
        fileData.append(fileDataList.get(fileDataList.size() - 1));

        return fileData.toString();
    }
}
