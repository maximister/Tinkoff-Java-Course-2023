package edu.hw8.task1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ServerTest {

    private static final String HOST = "localhost";
    private static final int PORT = 8888;
    Pattern RequestPattern = Pattern.compile("Клиент: ([А-Яа-я,.?!]+)");
    Pattern ResponsePattern = Pattern.compile("Сервер: ([А-Яа-я ,.?!\\[\\]]+)");

    @Test
    @DisplayName("testing client and server works")
    public void run_shouldCorrectlyProcessRequestsFromClients() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Path path = Path.of("src/main/java/edu/hw8/task1/quotes.txt");
        QuotesStorage storage = new QuotesStorage(path);
        List<String> tags = storage.getTags();
        Random random = ThreadLocalRandom.current();
        Matcher matcher;
        String response;
        String request;

        Client client = new Client(HOST, PORT);

        startServer(storage);

        client.connect(tags.get(random.nextInt(tags.size())));
        String data = outputStream.toString().replace("\u0000", "").trim();
        matcher = RequestPattern.matcher(data);
        matcher.find();
        request = matcher.group(1);
        matcher = ResponsePattern.matcher(data);
        matcher.find();
        response = matcher.group(1);

        assertThat(tags.contains(request)).isTrue();
        assertThat(storage.getQuotesListByTag(request).contains(response)).isTrue();
    }


    private void startServer(QuotesStorage storage) {

        Server server = new Server(4, PORT, storage);

        Thread serverThread = new Thread(server::start);
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
