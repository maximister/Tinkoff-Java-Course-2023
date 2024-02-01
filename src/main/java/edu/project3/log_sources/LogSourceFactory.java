package edu.project3.log_sources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LogSourceFactory {
    private final static String URL_PATTERN
        = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$";

    private LogSourceFactory() {
    }

    public static LogsSource getLogsSource(String source) {
        Pattern urlPattern = Pattern.compile(URL_PATTERN);
        Matcher matcher = urlPattern.matcher(source);

        if (matcher.matches()) {
            return new UrlLogLoader(source);
        } else {
            return new LocalFileLogLoader(source);
        }
    }
}
