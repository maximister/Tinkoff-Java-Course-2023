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
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LocalFileLogLoader implements LogsSource {
    private final List<Path> files;
    private final static String DELIMITER
        = System.getProperty("os.name").startsWith("Windows") ? "\\" : "/";

    private String source;
    private final static String NO_VALID_FILES
        = "there are no log files in your directory. Please try again";
    private final static Logger LOGGER = LogManager.getLogger();

    public LocalFileLogLoader(String directoryPath) {
        LOGGER.info("FileLoader was created");
        files = parseFilePaths(directoryPath);
    }

    private List<Path> parseFilePaths(String stringPath) {
        List<Path> matchedFiles = new ArrayList<>();
        Path dir = Path.of("src", "main", "java", "edu", "project3", "resources");
        source = stringPath.startsWith("\\") ? dir + stringPath : dir + DELIMITER + stringPath;
        PathMatcher pathMatcher = FileSystems.getDefault()
            .getPathMatcher("glob:" + "**/" + stringPath + "*");
        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(file)) {
                        if ("txt".equals(FilenameUtils.getExtension(file.toString()))) {
                            matchedFiles.add(file);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });

            if (matchedFiles.isEmpty()) {
                throw new RuntimeException(NO_VALID_FILES);
            }

            LOGGER.info("File path was parsed");
        } catch (IOException e) {
            throw new RuntimeException(NO_VALID_FILES);
        }
        return matchedFiles;
    }

    @Override
    public Stream<String> getLogs() {
        return files.stream().flatMap((file) -> {
            try {
                return Files.lines(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public String getSources() {
        return source;
    }
}