package edu.hw6.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.net.http.HttpResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

public class HackerNewsTest {
    @Test
    @DisplayName("getNews test")
    public void getNews_shouldReturnNewsTitle() {
        HackerNews hackerNews = new HackerNews();
        long id = 37570037;
        String expectedTitle = "JDK 21 Release Notes";

        assertThat(hackerNews.getNews(id)).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("getHackerNewsTopStories test")
    public void getHackerNewsTopStories_shouldReturnLongIdArray() throws Exception {
        String mockedTopNewsArray = "[123,234,1111111,11]";
        HttpResponse mockedResponse = Mockito.mock(HttpResponse.class);
        when(mockedResponse.body()).thenReturn(mockedTopNewsArray);

        RequestSettings settings = new RequestSettings(null, mockedResponse);
        HackerNews hackerNews = new HackerNews(settings);

        long[] expectedTop = new long[] {123, 234, 1111111, 11};

       assertThat(hackerNews.getHackerNewsTopStories()).isEqualTo(expectedTop);
    }
}
