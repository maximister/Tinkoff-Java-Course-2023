package edu.hw6.task5;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static edu.hw6.task5.RequestSettings.getDefaultSettings;

public class HackerNews {
    private final static String HACKER_NEWS_URL = "https://hacker-news.firebaseio.com/v0";
    private final static String TOP_STORIES = "/topstories.json";
    private final static String NEWS = "/item/%s.json";
    private final static Pattern TITLE_PATTERN = Pattern.compile("\"title\":\"([^\"]+)\"");

    private static RequestSettings requestSettings;

    public HackerNews() {
        this(getDefaultSettings());
    }

    public HackerNews(RequestSettings requestSettings) {
        HackerNews.requestSettings = requestSettings;
    }

    //добавил глаголы в названия методов с сайта, а то режет глаза
    public long[] getHackerNewsTopStories() {
        try {
            String news = sendRequest(HACKER_NEWS_URL + TOP_STORIES);
            return parseTopNews(news);
        } catch (RuntimeException e) {
            return new long[] {};
        }
    }

    private  long[] parseTopNews(String topNews) {
        //надеюсь так норм, или лучше отдельно разбить строку в массив, а затем уже делать страм отдельно
        return Arrays
            .stream(topNews.substring(1, topNews.length() - 1)
                .split(","))
            .mapToLong(Long::parseLong)
            .toArray();
    }

    public  String getNews(long id) {
        try {
            String newsEndpoint = String.format(NEWS, id);
            String news = sendRequest(HACKER_NEWS_URL + newsEndpoint);
            return getTitle(news);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error! please try again");
        }
    }

    private String getTitle(String news) {
        Matcher matcher = TITLE_PATTERN.matcher(news);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private String sendRequest(String url) {
        try {
            HttpClient client;
            if (!requestSettings.hasClient()) {
                client = requestSettings.client();
            } else {
                client = HttpClient.newBuilder().build();
            }
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
            HttpResponse<String> response;
            if (!requestSettings.hasResponse()) {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } else {
                response = requestSettings.response();
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
