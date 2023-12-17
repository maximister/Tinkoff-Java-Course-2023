package edu.hw8.task1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Semaphore;

public class RequestProcessor implements Runnable {

    private static final int BUFFER_SIZE = 1024;
    private static final String CLIENT = "Клиент: ";
    private static final String SERVER = "Сервер: ";
    private final QuotesStorage quotesStorage;
    private final SocketChannel client;
    private final Semaphore semaphore;

    public RequestProcessor(SocketChannel client, Semaphore semaphore, QuotesStorage storage) {
        this.client = client;
        this.semaphore = semaphore;
        this.quotesStorage = storage;
    }

    @Override
    public void run() {
        try (Selector selector = Selector.open()) {
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            while (client.isOpen()) {
                selector.select();
                for (SelectionKey key : selector.keys()) {
                    if (key.isReadable()) {
                        processRequest();
                        client.close();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

    @SuppressWarnings("checkstyle:RegexpSinglelineJava")
    private void processRequest() {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        int bytesRead = 0;

        try {
            bytesRead = client.read(buffer);
            if (bytesRead > 0) {
                String request = new String(buffer.array(), StandardCharsets.UTF_8);
                System.out.println(CLIENT + request);
                sendResponse(request);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendResponse(String request) {
        String responseMessage = quotesStorage.getQuote(request.trim());

        ByteBuffer response =
            ByteBuffer.wrap((SERVER
                + responseMessage).getBytes(StandardCharsets.UTF_8));

        while (response.hasRemaining()) {
            try {
                client.write(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
