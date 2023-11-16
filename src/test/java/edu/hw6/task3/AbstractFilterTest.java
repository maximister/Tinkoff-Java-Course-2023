package edu.hw6.task3;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.List;
import static edu.hw6.task3.AbstractFilter.extensionFilter;
import static edu.hw6.task3.AbstractFilter.globMatches;
import static edu.hw6.task3.AbstractFilter.isReadable;
import static edu.hw6.task3.AbstractFilter.largerThan;
import static edu.hw6.task3.AbstractFilter.lessThan;
import static edu.hw6.task3.AbstractFilter.magicNumber;
import static edu.hw6.task3.AbstractFilter.regexContains;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AbstractFilterTest {
    @Test
    @DisplayName("testng AbstractFilter")
    public void filtersTest() {
        DirectoryStream.Filter<Path> filter = getFilter();
        Path pathFile = Paths.get("src", "test", "java", "edu", "hw6", "task3", "resources");
        List<String> paths = new ArrayList<>();

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(pathFile, filter)) {
            entries.forEach(path -> paths.add(path.toString()));
            assertThat(paths.size() == 1
                && FilenameUtils.getName(paths.get(0)).equals("art-as.png")).isTrue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static DirectoryStream.Filter<Path> getFilter() {
        AbstractFilter regularFile = Files::isRegularFile;

        return regularFile
            .and(isReadable())
            .and(largerThan(1000))
            .and(lessThan(Long.MAX_VALUE))
            .and(magicNumber(0x89, 'P', 'N', 'G'))
            .and(globMatches("*.png"))
            .and(extensionFilter("png"))
            .and(regexContains("-"));
    }
}
