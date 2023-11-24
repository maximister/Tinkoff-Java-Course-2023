package edu.project3.renderers;

import edu.project3.metrics.MetricsContainer;
import edu.project3.metrics.MetricsRow;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MarkdownRenderer extends AbstractRenderer {

    private final static String WALL = "|";
    private final static Logger LOGGER = LogManager.getLogger();

    public MarkdownRenderer() {
        LOGGER.info("markdown Renderer was created");
    }

    @Override
    public String addHeader(String header) {
        return "#### " + header;
    }

    @Override
    public String addRow(List<String> row, int[] columnWidth) {
        StringBuilder renderedRow = new StringBuilder();
        renderedRow.append(WALL);
        for (int i = 0; i < row.size(); i++) {
            String cell = row.get(i);
            int whitespace = columnWidth[i] - cell.length();
            renderedRow.append(" ".repeat(whitespace)).append(cell);
            renderedRow.append(WALL);
        }
        renderedRow.append("\n");

        return renderedRow.toString();
    }

    @Override
    public String getRenderedTable(MetricsContainer table, int cols) {
        StringBuilder renderedTable = new StringBuilder();
        renderedTable.append(addHeader(table.getHeader())).append("\n\n");

        int[] columnsWidth = computeColumnsWidth(table, cols);
        List<MetricsRow> rows = table.getTableRows();

        renderedTable.append(addRow(rows.get(0).values(), columnsWidth));
        renderedTable.append(addTableHeader(columnsWidth));
        for (int i = 1; i < rows.size(); i++) {
            renderedTable.append(addRow(rows.get(i).values(), columnsWidth));
        }

        LOGGER.info("Table " + table.getHeader() + " was rendered");
        return renderedTable.toString();
    }

    @Override
    public String addTableHeader(int[] columnsWidth) {
        StringBuilder header = new StringBuilder();
        header.append(WALL);

        header.append(":").append("-".repeat(columnsWidth[0] - 1)).append(WALL);
        for (int i = 1; i < columnsWidth.length - 1; i++) {
            header.append(":").append("-".repeat(columnsWidth[i] - 2)).append(":").append(WALL);
        }
        header.append("-".repeat(columnsWidth[columnsWidth.length - 1] - 1)).append(":").append(WALL);
        header.append("\n");

        return header.toString();
    }
}
