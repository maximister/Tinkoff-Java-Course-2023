package edu.hw6.task6;

public record Port(Protocol protocol, int port, String service) {
    public enum Protocol {
        UDP,
        TCP
    }
}
