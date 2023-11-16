package edu.hw6.task6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static edu.hw6.task6.PortRequestParser.getPortServices;

public final class PortScanner {
    private final static int UPPER_PORTS_BOUND = 49151;

    private final static Map<Integer, String> PORT_MAP = getPortServices();

    private PortScanner() {
    }

    @SuppressWarnings("checkstyle:EmptyBlock")
    public static List<Port> scanPorts() {
        List<Port> scannedPorts = new ArrayList<>();
        for (int i = 0; i <= UPPER_PORTS_BOUND; i++) {
            try (ServerSocket socket = new ServerSocket(i)) {
            } catch (IOException e) {
                scannedPorts.add(new Port(Port.Protocol.TCP, i, getServiceName(i)));
            }
            try (DatagramSocket socket = new DatagramSocket(i)) {
            } catch (SocketException e) {
                scannedPorts.add(new Port(Port.Protocol.UDP, i, getServiceName(i)));
            }
        }

        return scannedPorts.isEmpty() ? Collections.emptyList() : scannedPorts;
    }

    private static String getServiceName(int port) {
        if (PORT_MAP.containsKey(port)) {
            return PORT_MAP.get(port);
        }
        return "";
    }
}
