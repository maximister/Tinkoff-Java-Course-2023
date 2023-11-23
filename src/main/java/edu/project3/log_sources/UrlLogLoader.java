package edu.project3.log_sources;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UrlLogLoader implements LogsSource {
    private String logs;
    private final String source;
    private final static Logger LOGGER = LogManager.getLogger();


    public UrlLogLoader(String url) {
        LOGGER.info("URL log loader was created");
        source = url;
        sendRequest(url);
    }

    public List<String> parseRequest() {
        return List.of(logs.split("\n"));
    }

    private void sendRequest(String url) {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            logs = response.body();
            LOGGER.info("Logs were successfully loaded");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getLogs() {
        LOGGER.info("Logs list was created");
        return parseRequest();
    }

    @Override
    public String getSources() {
        return source;
    }
}
