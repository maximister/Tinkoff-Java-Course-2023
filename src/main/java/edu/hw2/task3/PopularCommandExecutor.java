package edu.hw2.task3;

import edu.hw2.task3.connection_managers.ConnectionManager;
import edu.hw2.task3.connections.Connection;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("параметр maxAttemps должен быть больше 0");
        }
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    void tryExecute(String command) {
        Exception cause = null;
        for (int i = 0; i < maxAttempts; i++) {
            try (Connection con = manager.getConnection()) {
                con.execute(command);
                break;
            } catch (Exception e) {
                cause = e;
            }
        }

        if (cause != null) {
            throw new ConnectionException(cause);
        }
    }
}
