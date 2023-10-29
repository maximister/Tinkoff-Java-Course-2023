package edu.hw2.task3.connection_managers;

import edu.hw2.task3.connections.Connection;
import edu.hw2.task3.connections.FaultyConnection;
import edu.hw2.task3.connections.StableConnection;
import java.util.Random;

public class DefaultConnectionManager implements ConnectionManager {
    private static final int FAULTY_CHANCE = 2;
    private final Random random = new Random();

    @Override
    public Connection getConnection() {
        return ((random.nextInt(FAULTY_CHANCE) + 1) % FAULTY_CHANCE == 0)
            ? new FaultyConnection() : new StableConnection();
    }
}
