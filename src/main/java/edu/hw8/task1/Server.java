package edu.hw8.task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Server {
    private final int maxConnetions;
    private final int port;
    private final QuotesStorage quotesStorage;
    private final ExecutorService executor;
    private final Semaphore semaphore;

    public Server(int maxConnections, int port, QuotesStorage storage) {
        this.port = port;
        this.maxConnetions = maxConnections;
        quotesStorage = storage;
        executor = Executors.newFixedThreadPool(maxConnections);
        semaphore = new Semaphore(maxConnections);
    }

    public void start() {
        try (ServerSocketChannel channel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            channel.bind(new InetSocketAddress(port));

            while (channel.isOpen()) {
                selector.select();
                for (SelectionKey key : selector.keys()) {
                    if (semaphore.tryAcquire()) {
                        if (key.isAcceptable()) {
                            executor.execute(new RequestProcessor(channel.accept(), semaphore));
                        }
                        selector.selectedKeys().remove(key);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
