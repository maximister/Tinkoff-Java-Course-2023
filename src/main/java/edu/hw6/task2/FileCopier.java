package edu.hw6.task2;

import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FilenameUtils;

public final class FileCopier {
    private final static String COPY_PATTERN = " — копия";

    private FileCopier() {
    }

    public static Path cloneFile(Path path) {
        if (!Files.exists(path)) {
            throw new FileSystemNotFoundException("your file does not exist!");
        }
        boolean isCopied = false;
        int copyNumber = 0;
        while (!isCopied) {
            try {
                String copyName = getCopyName(path.getFileName().toString(), copyNumber);
                Path copy = path.getParent().resolve(copyName);
                if (Files.exists(copy)) {
                    copyNumber++;
                } else {
                    Files.copy(path, copy);
                    return copy;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    private static String getCopyName(String file, int copyNumber) {

        StringBuilder newName = new StringBuilder();
        String extension = FilenameUtils.getExtension(file);
        String name = FilenameUtils.getBaseName(file);

        newName.append(name).append(COPY_PATTERN);
        if (copyNumber != 0) {
            newName.append(" (").append(copyNumber).append(')');
        }

        newName.append('.').append(extension);

        return newName.toString();
    }
}
