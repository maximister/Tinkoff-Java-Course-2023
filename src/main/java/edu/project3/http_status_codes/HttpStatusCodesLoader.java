package edu.project3.http_status_codes;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpStatusCodesLoader {

    private final Path fileForSaving;
    private static final Logger LOGGER = LogManager.getLogger();
    private final static String URL
        = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_"
        + "%D0%BA%D0%BE%D0%B4%D0%BE%D0%B2_%D1%81%D0%BE%D1%81%D1%82%D0%BE%D1%8F%D0%BD%D0%B8%D1%8F_HTTP";
    private final static Pattern STATUS_PATTERN = Pattern.compile("(\\d{3}) ([\\w ’]+)");

    public HttpStatusCodesLoader(Path file) {
        fileForSaving = file;
    }

    public void saveCodesToFile() {
        try (FileWriter writer = new FileWriter(fileForSaving.toString(), false)) {
            Document doc = Jsoup.connect(URL).get();
            doc.select(".mw-content-ltr")
                .select("ul")
                .select("ul")
                .select("li")
                .select("ul")
                .select("a")
                .forEach(line -> {
                Matcher matcher = STATUS_PATTERN.matcher(line.text());
                while (matcher.find()) {
                    try {
                        writer.write(matcher.group(1) + ":" + matcher.group(2) + "\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            LOGGER.info("Http status codes list was created");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
