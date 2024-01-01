package edu.hw9.task2;

import java.io.File;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;

public final class FileTasksHandler {
    private FileTasksHandler() {
    }

    public static List<File> filterDirectoriesByFilesAmount(File file, int minFilesCount) {
        DirectorySearchTask task = new DirectorySearchTask(file, minFilesCount);
        List<File> result;
        try (ForkJoinPool pool = new ForkJoinPool()) {
            result = pool.invoke(task);
            pool.shutdown();
        }
        return result;
    }

    public static List<File> filterFilesByPredicate(File file, Predicate<File> predicate) {
        FilterFilesByPredicateTask task = new FilterFilesByPredicateTask(file, predicate);
        List<File> result;
        try (ForkJoinPool pool = new ForkJoinPool()) {
            result = pool.invoke(task);
            pool.shutdown();
        }
        return result;
    }
}
