package edu.hw2.task3;

import edu.hw2.task3.connection_managers.ConnectionManager;
import edu.hw2.task3.connection_managers.DefaultConnectionManager;
import edu.hw2.task3.connection_managers.FaultyConnectionManager;
import edu.hw2.task3.connections.FaultyConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PopularCommandExecutorTest {

    @ParameterizedTest
    @CsvSource(value = {
        "0",
        "-10"
    })
    @DisplayName("Проверка корректности работы программы при передаче некорректного maxAttempts")
    public void IllegalMaxAttemptsTest(int value) {

        assertThrows(
            IllegalArgumentException.class,
            () -> new PopularCommandExecutor(new DefaultConnectionManager(), value)
        );
    }

    @ParameterizedTest
    @MethodSource("updatePackagesArgs")
    @DisplayName("Тестирование PopularCommandExecutor")
    void updatePackagesTest(ConnectionManager manager) {
        PopularCommandExecutor pce = new PopularCommandExecutor(manager, 12);

        assertThrows(ConnectionException.class, pce::updatePackages);

    }

    static Stream<Arguments> updatePackagesArgs() {
        DefaultConnectionManager dcm = Mockito.mock(DefaultConnectionManager.class);
        FaultyConnectionManager fcm = Mockito.mock(FaultyConnectionManager.class);
        FaultyConnection fc = Mockito.mock(FaultyConnection.class);
        Mockito.doThrow(new ConnectionException()).when(fc).execute(anyString());
        Mockito.when(dcm.getConnection()).thenReturn(fc);
        Mockito.when(fcm.getConnection()).thenReturn(fc);

        return Stream.of(
            Arguments.of(dcm),
            Arguments.of(fcm)
        );
    }

}
