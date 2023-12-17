package edu.hw8.task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    private static final int BUFFER_SIZE = 1024;
    private final InetSocketAddress hostAddress;

    public Client(String host, int port) {
        this.hostAddress = new InetSocketAddress(host, port);
    }

    @SuppressWarnings("checkstyle:RegexpSinglelineJava")
    public void connect(String messageToSend) {
        try (SocketChannel client = SocketChannel.open(hostAddress)) {
            ByteBuffer buffer = ByteBuffer.wrap(messageToSend.getBytes(StandardCharsets.UTF_8));
            while (buffer.hasRemaining()) {
                client.write(buffer);
            }
            buffer.flip();
            buffer = ByteBuffer.allocate(BUFFER_SIZE);
            int bytesRead;
            while ((bytesRead = client.read(buffer)) != -1) {
                if (bytesRead == 0) {
                    continue;
                }
                System.out.println(new String(buffer.array(), StandardCharsets.UTF_8));
                break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
