package edu.hw8.task1;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class RequestProcessor implements Runnable {

    private static final int BUFFER_SIZE = 1024;
    private static final String CLIENT = "Клиент: ";
    private static final String SERVER = "Сервер: ";
    private static final String NO_RESPONSE_MESSAGE = "Нет ответа на данное сообщение :(";
    private static final Map<String, String> RESPONSES = Map.of(
        "личности", "Не переходи на личности там, где их нет",
        "оскорбления", "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
        "глупый", "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
        "интеллект", "Чем ниже интеллект, тем громче оскорбления"
    );
    private final SocketChannel client;
    private final Semaphore semaphore;

    public RequestProcessor(SocketChannel client, Semaphore semaphore) {
        this.client = client;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {

    }
}
