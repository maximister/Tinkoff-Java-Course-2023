package edu.hw6.task6;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/*не хотел вручную писать мапу с популярными портами,
  поэтому нашкодил тут что-то, чтобы взять с таблички на сайте с источников,
  но там маловоато портов, поэтому итоговая таблица получилась скромной*/
public class PortRequestParser {
    private static final Pattern PARSING_PATTERN1 = Pattern.compile("Port (\\d+): ([\\w ]+)");
    private static final Pattern PARSING_PATTERN2 = Pattern.compile("\\w+ \\((\\d+)\\) - ([\\w ]+)");

    private PortRequestParser() {
    }

    public static String parseRequest(String url) {
        try {
            StringBuilder ports = new StringBuilder();
            Document doc = Jsoup.connect(url).get();
            doc.select(".list").select("li").stream()
                .forEach(line -> {
                    Matcher matcher = PARSING_PATTERN1.matcher(line.text());
                    if (matcher.find()) {
                        ports.append(matcher.group(1))
                            .append(";")
                            .append(matcher.group(2))
                            .append("\n");
                    } else {
                        matcher = PARSING_PATTERN2.matcher(line.text());
                        if (matcher.find()) {
                            ports.append(matcher.group(1))
                                .append(";")
                                .append(matcher.group(2))
                                .append("\n");
                        }
                    }
                });

            return ports.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<Integer, String> getPortServices() {
        String url = "https://www.tutorialspoint.com/50-common-ports-you-should-know";
        String stringToParse = parseRequest(url);

        Map<Integer, String> portMap = new HashMap<>();
        Arrays.stream(stringToParse.split("\n"))
            .forEach(line -> {
                String[] splittedLine = line.split(";");
                portMap.put(Integer.parseInt(splittedLine[0]), splittedLine[1]);
            });

        return portMap;
    }
}
