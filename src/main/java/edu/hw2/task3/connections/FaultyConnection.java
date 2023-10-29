package edu.hw2.task3.connections;

import edu.hw2.task3.ConnectionException;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {
    private static final int FAULTY_CHANCE = 2;
    private final Random random = new Random();
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(String command) {
        if ((random.nextInt(FAULTY_CHANCE) + 1) % FAULTY_CHANCE == 0) {
            LOGGER.info("Error: command " + command + " was not executed!");
            throw new ConnectionException();
        } else {
            LOGGER.info("command " + command + " was successfully executed");
        }
    }

    @Override
    public void close() throws Exception {
    }
}
