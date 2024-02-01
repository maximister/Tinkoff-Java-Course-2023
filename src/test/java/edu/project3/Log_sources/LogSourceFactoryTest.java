package edu.project3.Log_sources;

import edu.project3.log_sources.LocalFileLogLoader;
import edu.project3.log_sources.LogsSource;
import edu.project3.log_sources.UrlLogLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project3.log_sources.LogSourceFactory.getLogsSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogSourceFactoryTest {
    @Test
    @DisplayName("getting log source with correct url source")
    public void getLogsSource_shouldReturnUrlLogsSource() {
        String url
            = "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
        LogsSource logsSourceTest = getLogsSource(url);

        assertThat(logsSourceTest instanceof UrlLogLoader).isTrue();
    }

    @Test
    @DisplayName("getting log source with correct file source")
    public void getLogsSource_shouldReturnFileLogsSource() {
        String file = "localLogs.txt";
        LogsSource logsSourceTest = getLogsSource(file);

        assertThat(logsSourceTest instanceof LocalFileLogLoader).isTrue();
    }

    @Test
    @DisplayName("getting log source with invalid file source")
    public void getLogsSource_shouldThrowException() {
        String file = "joeBuba.txt";

        assertThrows(
            RuntimeException.class,
            () -> getLogsSource(file)
        );
    }
}
