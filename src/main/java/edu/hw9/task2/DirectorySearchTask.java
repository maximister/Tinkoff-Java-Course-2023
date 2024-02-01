package edu.hw9.task2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;

public class DirectorySearchTask extends AbstractFileTask {
    //я не очень хочу искать директории с 1000+ файлов, плюс не уверен,
    // что это можно будет адекватно тестить так, чтобы тесты на гите проходили, так что задам сам минимальный порог
    private final int requiredMinimalFilesAmount;

    public DirectorySearchTask(File directory, int requiredMinimalFilesAmount) {
        super(directory);
        this.requiredMinimalFilesAmount = requiredMinimalFilesAmount;
    }

    @Override
    protected List<File> processFiles(File[] filesInDirectory) {
        List<ForkJoinTask<List<File>>> tasks = new ArrayList<>();
        List<File> result = new ArrayList<>();

        int filesAmout = 0;
        for (File currentFile : filesInDirectory) {
            if (currentFile.isFile()) {
                filesAmout++;
                continue;
            }
            DirectorySearchTask task = new DirectorySearchTask(currentFile, requiredMinimalFilesAmount);
            tasks.add(task.fork());
        }

        if (filesAmout >= requiredMinimalFilesAmount) {
            result.add(directory);
        }

        tasks.stream()
            .flatMap(t -> t.join().stream())
            .forEach(result::add);

        return result;
    }
}
