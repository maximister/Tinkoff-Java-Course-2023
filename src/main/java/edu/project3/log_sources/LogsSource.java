package edu.project3.log_sources;

import java.util.stream.Stream;

public interface LogsSource {
    String getSources();

    Stream<String> getLogs();
}
