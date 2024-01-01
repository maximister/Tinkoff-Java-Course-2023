package edu.hw9.task2;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public abstract class AbstractFileTask extends RecursiveTask<List<File>> {
    protected final File directory;

    public AbstractFileTask(File directory) {
        this.directory = directory;
    }

    @Override
    protected List<File> compute() {
        File[] filesInDirectory = directory.listFiles();
        if (filesInDirectory == null) {
            return Collections.emptyList();
        }

        return processFiles(filesInDirectory);
    }

    protected abstract List<File> processFiles(File[] filesInDirectory);
}
