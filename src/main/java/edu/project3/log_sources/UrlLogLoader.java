package edu.project3.log_sources;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UrlLogLoader implements LogsSource {
    private String logs;
    private final String source;
    private final static Logger LOGGER = LogManager.getLogger();

    public UrlLogLoader(String url) {
        LOGGER.info("URL log loader was created");
        source = url;
    }

    @SneakyThrows
    @Override
    public Stream<String> getLogs() {
        URL url = new URI(source).toURL();
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        LOGGER.info("Logs were successfully loaded");
        return new BufferedReader(
            new InputStreamReader(in, StandardCharsets.UTF_8))
            .lines();
    }

    @Override
    public String getSources() {
        return source;
    }
}
