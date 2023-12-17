package edu.hw8.task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class QuotesStorage {
    private final Map<String, List<String>> quotes;
    private final Set<String> tags;
    private final static String DELIMITER = ":";
    private final static String TAGS_DELIMITER = ",";
    private final static Random RANDOM = ThreadLocalRandom.current();

    public QuotesStorage(Path qoutesPath) {
        quotes = new HashMap<>();
        tags = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(qoutesPath.toString()))) {
            reader.lines()
                .map(line -> line.split(DELIMITER))
                .forEach(line -> {
                    for (String tag : line[0].split(TAGS_DELIMITER)) {
                        quotes.computeIfAbsent(tag, t -> new ArrayList<>()).add(line[1]);
                        tags.add(tag);
                    }
                });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public QuotesStorage() {
        quotes = new HashMap<>();
        this.addQuote("личности", "Не переходи на личности там, где их нет");
        this.addQuote(
            "оскорбления",
            "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами"
        );
        this.addQuote(
            "глупый",
            "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма"
        );
        this.addQuote(
            "интеллект",
            "Чем ниже интеллект, тем громче оскорбления"
        );

        tags = quotes.keySet();
    }

    public void addQuote(String tag, String quote) {
        quotes.computeIfAbsent(tag, t -> new ArrayList<>()).add(quote);
    }

    public String getQuote(String tag) {
        List<String> answers = quotes.getOrDefault(tag, Collections.emptyList());
        return answers.isEmpty() ? "Абстракция понятна, в  детали реализации вникаю..."
            : answers.get(RANDOM.nextInt(answers.size()));
    }

    public List<String> getQuotesListByTag(String tag) {
        return quotes.getOrDefault(tag, Collections.emptyList());
    }

    public List<String> getTags() {
        return tags.stream().toList();
    }
}
