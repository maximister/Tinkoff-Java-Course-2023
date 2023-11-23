package edu.hw6.task6;

import java.util.List;
import net.steppschuh.markdowngenerator.table.Table;

public final class MarkdownPortsTableGenerator {

    private MarkdownPortsTableGenerator() {
    }

    //таблица с занятыми портами, чей владелец известен
    /*немного лень форматировать строки, поэтому нагуглил библиотеку с генератором md таблиц*/
    public static String getPortTable(List<Port> ports) {
        Table.Builder tableBuilder = new Table.Builder()
            .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_LEFT, Table.ALIGN_LEFT)
            .addRow("Protocol", "Port", "Service");

        for (Port port : ports) {
            if (!port.service().isEmpty()) {
                tableBuilder.addRow(port.protocol(), port.port(), port.service());
            }
        }

        return tableBuilder.build().toString();
    }
}
