package edu.hw6.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DiscMapTest {
    public String getFileContent(String file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();

            if (reader.ready()) {
                reader.lines().map(line -> line.split(":"))
                    .forEach(splittedLine -> {
                        content.append(splittedLine[0]).append('=').append(splittedLine[1]).append(", ");
                    });
            }
            if (content.length() > 2) {
                return '[' + content.substring(0, content.length() - 2) + ']';
            }
            return "[]";

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeTestFile(String file) {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write("key1:val1\n");
            writer.write("key2:val2\n");
            writer.write("key3:val3\n");
            writer.write("key4:val4");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("DiscMap test")
    public void DiscMapCommonTest() {
        String testFile = "testFile.txt";
        DiscMap discMap = new DiscMap(testFile);

        //file and map is empty
        assertThat(discMap.entrySet().toString()).isEqualTo(getFileContent(testFile));

        discMap.put("1", "a");
        discMap.put("2", "b");
        discMap.put("3", "c");

        //file content and map content are equal
        assertThat(discMap.entrySet().toString()).isEqualTo(getFileContent(testFile));

        DiscMap discMap2 = new DiscMap(testFile);

        //content of a new map is equal to other map with the same file (coping map by files)
        assertThat(discMap.entrySet().toString()).isEqualTo(discMap2.entrySet().toString());


        //в тестах какой-то колхоз с удалением файлов, тк иначе все ломается
        File file = new File(testFile);
        file.delete();
    }

    @Test
    @DisplayName("removing element test")
    public void removeTest() {
        String testFile = "testFile.txt";
        DiscMap discMap = new DiscMap(testFile);
        discMap.put("1", "a");
        discMap.put("2", "b");
        discMap.put("3", "c");
        discMap.remove("2");

        assertThat(discMap.entrySet().toString()).isEqualTo(getFileContent(testFile));

        File file = new File(testFile);
        file.delete();
    }

    @Test
    @DisplayName("coping map content to other file by save method")
    public void saveTest() {
        String testFile = "testFile.txt";
        String testFile2 = "testFile2.txt";
        DiscMap discMap = new DiscMap(testFile);
        discMap.put("1", "a");
        discMap.put("2", "b");

        discMap.save(testFile2);

        assertThat(discMap.entrySet().toString()).isEqualTo(getFileContent(testFile2));

        File file = new File(testFile);
        file.delete();
        file = new File(testFile2);
        file.delete();
    }

    @Test
    @DisplayName("adding data from other file by load method")
    public void loadTest() {
        String testFile = "testFile.txt";
        String additionalFile = "addFile.txt";
        makeTestFile(additionalFile);
        DiscMap discMap = new DiscMap(testFile);

        //adding data from additionalFile
        discMap.load(additionalFile);
        String mapData = discMap.entrySet().toString();

        assertThat(mapData).contains("key1=val1");
        assertThat(mapData).contains("key2=val2");
        assertThat(mapData).contains("key3=val3");
        assertThat(mapData).contains("key4=val4");

        File file = new File(testFile);
        file.delete();
        file = new File(additionalFile);
        file.delete();
    }

    @Test
    @DisplayName("insertion of other map by putAll")
    public void putAllTest() {
        String testFile = "testFile.txt";
        DiscMap discMap = new DiscMap(testFile);

        Map<String, String> insertedMap = new HashMap<>();
        insertedMap.put("ins1", "v1");
        insertedMap.put("ins2", "v2");

        discMap.putAll(insertedMap);
        String discMapData = discMap.entrySet().toString();

        assertThat(discMapData.contains("ins1=v1") && discMapData.contains("ins2=v2"))
            .isTrue();

        File file = new File(testFile);
        file.delete();
    }

    @Test
    @DisplayName("invalidFilenameTest")
    public void invalidFilenameTest() {
        String fileName = "testFile.txt";

        assertThrows(
            RuntimeException.class,
            () -> new DiscMap(null)
        );

        DiscMap map = new DiscMap(fileName);

        assertThrows(
            RuntimeException.class,
            () -> map.save(null)
        );

        assertThrows(
            RuntimeException.class,
            () -> map.load(null)
        );
    }

}
