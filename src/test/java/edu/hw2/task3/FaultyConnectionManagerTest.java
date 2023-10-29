package edu.hw2.task3;

import edu.hw2.task3.connection_managers.FaultyConnectionManager;
import edu.hw2.task3.connections.Connection;
import edu.hw2.task3.connections.StableConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FaultyConnectionManagerTest {
    @Test
    @DisplayName("Проверка getConnection")
    public void getConnectionTest() {
        FaultyConnectionManager fcm = new FaultyConnectionManager();
        boolean flag = true;

        for (int i = 0; i < 10; i++) {
            Connection connection = fcm.getConnection();
            if (connection instanceof StableConnection) {
                flag = false;
            }
        }

        assertThat(flag).isTrue();
    }
}
