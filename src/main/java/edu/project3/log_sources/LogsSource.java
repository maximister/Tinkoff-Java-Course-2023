package edu.project3.log_sources;

import java.util.List;

public interface LogsSource {
    List<String> getLogs();

    String getSources();
}
