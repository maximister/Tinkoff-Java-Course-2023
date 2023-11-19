package edu.project3.log_sources;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlLogLoader implements LogsSource {
    private String logs;

//    private final static String INVALID_URL_MESSAGE
//        = "Error! Your URL has invalid format. Please try again";

    public UrlLogLoader(String url) {
        //TODO: удалить, тк проверка теперь в другом классе
//        Pattern urlPattern = Pattern.compile(URL_PATTERN);
//        Matcher matcher = urlPattern.matcher(url);
//        if (!matcher.matches()) {
//            throw new IllegalArgumentException(INVALID_URL_MESSAGE);
//        }

        sendRequest(url);
    }

    public List<String> parseRequest() {
        //TODO: вполне возможно, это тут не нужно
        if (logs == null || logs.isEmpty()) {
            throw new IllegalArgumentException();
        }

        //TODO: просить проверяющего про необходимость файлов
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
        } catch (IOException | InterruptedException e) {
            //TODO: подумать, как представить ошибку получше
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getLogs() {
        return parseRequest();
    }
}
