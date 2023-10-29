package edu.hw2.task3;

import edu.hw2.task3.connection_managers.DefaultConnectionManager;
import edu.hw2.task3.connections.Connection;
import edu.hw2.task3.connections.FaultyConnection;
import edu.hw2.task3.connections.StableConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DefaultConnectionManagerTest {
    @Test
    @DisplayName("Проверка getConnection")
    public void getConnectionTest() {
        DefaultConnectionManager dcm = new DefaultConnectionManager();
        boolean stableFlag = false;
        boolean faultyFlag = false;

        for (int i = 0; i < 10; i++) {
            Connection connection = dcm.getConnection();
            if (connection instanceof StableConnection) {
                stableFlag = true;
            } else if (connection instanceof FaultyConnection) {
                faultyFlag = true;
            }
        }

        assertThat(stableFlag).isTrue();
        assertThat(faultyFlag).isTrue();
    }
}
