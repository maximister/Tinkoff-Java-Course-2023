package edu.hw6.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import static edu.hw6.task6.MarkdownPortsTableGenerator.getPortTable;
import static edu.hw6.task6.PortRequestParser.parseRequest;
import static edu.hw6.task6.PortScanner.scanPorts;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task6Test {
    /*не особо понятно, что тут подробно тестить, да и мокать немного лень,
      так что возможно тут будут тесты для галочки скорее(*/
    @Test
    @DisplayName("test that list of used ports are not empty")
    public void scanPorts_shouldReturnNotEmptyList() {
        assertThat(scanPorts()).isNotEqualTo(Collections.emptyList());
    }

    @Test
    @DisplayName("testing parseRequest method with invalid URL")
    public void parseRequestWithInvalidUrl_shouldThrowRuntimeException() {
        String url = "https://downloadKuhnyaSerialHD.com";

        assertThrows(
            RuntimeException.class,
            () -> {
                parseRequest(url);
            }
        );
    }

    @ParameterizedTest
    @MethodSource("getTestPortList")
    @DisplayName("Testing MD table generator")
    public void getPortTable_shouldReturnMdTable(List<Port> ports) {
        assertThat(checkPortTable(getPortTable(ports))).isTrue();
    }

    private static Stream<Arguments> getTestPortList() {
        List<Port> ports = List.of(
            new Port(Port.Protocol.UDP, 1, "testRow1"),
            new Port(Port.Protocol.TCP, 2, "testRow2"),
            new Port(Port.Protocol.UDP, 3, "testRow3")
        );

        return Stream.of(
            Arguments.of(ports)
        );
    }

    private static boolean checkPortTable(String table) {
        if (!table.contains("Protocol")) {
            return false;
        } else if (!table.contains("Port")) {
            return false;
        } else if (!table.contains("Service")) {
            return false;
        }

        //table has header => norm
        return true;
    }
}
