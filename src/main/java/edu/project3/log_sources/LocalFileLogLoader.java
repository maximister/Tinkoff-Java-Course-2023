package edu.project3.log_sources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;

public class LocalFileLogLoader implements LogsSource {
    private final List<Path> files;
    private final static String NO_VALID_FILES
        = "there are no log files in your directory/ Please try again";

    public LocalFileLogLoader(Path directoryPath) {
        if (Files.isDirectory(directoryPath)) {
            files = getFilesFromDirectory(directoryPath);
        } else {
            if (isValidPath(directoryPath)) {
                files = List.of(directoryPath);
            } else {
                throw new IllegalArgumentException(NO_VALID_FILES);
            }
        }
    }

    private boolean isValidPath(Path path) {
        return Files.exists(path) && "txt".equals(FilenameUtils.getExtension(path.toString()));
    }

    private List<Path> getFilesFromDirectory(Path directoryPath) {
        try (Stream<Path> paths = Files.walk(directoryPath)) {
            return paths
                .filter(Files::isRegularFile)
                .filter(this::isValidPath)
                .toList();
        } catch (IOException e) {
            throw new IllegalArgumentException(NO_VALID_FILES);
        }
    }

    private List<String> getLogsFromFile(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getLogs() {
        List<String> logsList = new ArrayList<>();
        files.forEach(file -> {
            logsList.addAll(getLogsFromFile(file));
        });

        return logsList;
    }
}
