package edu.hw9.task2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Predicate;

public class FilterFilesByPredicateTask extends AbstractFileTask {
    private final Predicate<File> predicate;

    public FilterFilesByPredicateTask(File directory, Predicate<File> predicate) {
        super(directory);
        this.predicate = predicate;
    }

    @Override
    protected List<File> processFiles(File[] filesInDirectory) {
        List<ForkJoinTask<List<File>>> tasks = new ArrayList<>();
        List<File> result = new ArrayList<>();

        for (File currentFile : filesInDirectory) {
            if (currentFile.isDirectory()) {
                FilterFilesByPredicateTask task = new FilterFilesByPredicateTask(currentFile, predicate);
                tasks.add(task.fork());
            } else if (predicate.test(currentFile)) {
                result.add(currentFile);
            }
        }
        tasks.stream()
            .flatMap(t -> t.join().stream())
            .forEach(result::add);
        return result;
    }
}
