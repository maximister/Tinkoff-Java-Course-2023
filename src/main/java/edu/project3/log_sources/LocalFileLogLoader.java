package edu.project3.log_sources;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class LocalFileLogLoader implements LogsSource {
    private final List<Path> files;
    private final static String NO_VALID_FILES
        = "there are no log files in your directory. Please try again";

    public LocalFileLogLoader(String directoryPath) {
        files = parseFilePaths(directoryPath);
    }

    private  List<Path> parseFilePaths(String stringPath) {
        List<Path> matchedFiles = new ArrayList<>();
        Path dir = Path.of("src", "main", "java", "edu", "project3", "resources");
        PathMatcher pathMatcher = FileSystems.getDefault()
            .getPathMatcher("glob:" + "**/" + stringPath + "*");
        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(file)) {
                        matchedFiles.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(NO_VALID_FILES);
        }
        return matchedFiles;
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
