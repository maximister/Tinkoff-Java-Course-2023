package edu.hw6.task5;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

//жестко накостылил для теста
public record RequestSettings(HttpClient client, HttpResponse<String> response) {

    public static RequestSettings getDefaultSettings() {
        return new RequestSettings(HttpClient.newBuilder().build(), null);
    }

    public boolean hasResponse() {
        return response != null;
    }

    public boolean hasClient() {
        return client != null;
    }
}
