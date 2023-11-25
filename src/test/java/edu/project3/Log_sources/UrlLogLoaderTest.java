package edu.project3.Log_sources;

import edu.project3.log_sources.UrlLogLoader;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UrlLogLoaderTest {
    @Test
    @DisplayName("testing working process with correct url")
    public void getLogs_ShouldReturnCorrectList() {
        String url
            = "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

        //тут накосячил со структурой класса наверное. По-хорошему нужно бы мокнуть response,
        // но тогда его нужно передавать в конструктор как какие-нибудь настройки, как я делал в 6 дз
        //но тк я планировал еще переделать саму подачу аргментов в этих классах
        // пока будет костыль)

        UrlLogLoader urlLogLoader = new UrlLogLoader(url);

        List<String> top5Logs = urlLogLoader.getLogs().stream().limit(5).toList();
        List<String> expected = List.of(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - - [17/May/2015:08:05:23 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "80.91.33.133 - - [17/May/2015:08:05:24 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"",
            "217.168.17.5 - - [17/May/2015:08:05:34 +0000] \"GET /downloads/product_1 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
            "217.168.17.5 - - [17/May/2015:08:05:09 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\""
        );
        assertThat(top5Logs).isEqualTo(expected);
        assertThat(urlLogLoader.getSources()).isEqualTo(url);
    }

    @Test
    @DisplayName("testing throwing exception with invalid url")
    public void getLogs_ShouldTrowException() {
        String url = "https://tinkoffvzlommodmnogodeneg.com";
        assertThrows(
            RuntimeException.class,
            () -> new UrlLogLoader(url)
        );
    }
}
