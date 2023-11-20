package edu.project3.renderers;

import edu.project3.metrics.MetricsContainer;
import edu.project3.metrics.MetricsRow;
import java.util.Arrays;
import java.util.List;

public class AdocRenderer extends AbstractRenderer {

    private final static String WALL = "|";
    private final static String ROW = "=";

    public AdocRenderer() {
    }

    @Override
    public String getRenderedTable(MetricsContainer table, int cols) {
        StringBuilder renderedTable = new StringBuilder();
        renderedTable.append(addHeader(table.getHeader())).append("\n\n");

        int[] columnsWidth = computeColumnsWidth(table, cols);
        List<MetricsRow> rows = table.getTableRows();

        renderedTable.append(addTableHeader(columnsWidth));
        for (int i = 0; i < rows.size(); i++) {
            renderedTable.append(addRow(rows.get(i).values(), columnsWidth));
        }
        renderedTable.append(addTableHeader(columnsWidth));
        return renderedTable.toString();
    }

    @Override
    public String addHeader(String header) {
        return "==== " + header;
    }

    @Override
    public String addRow(List<String> row, int[] columnsWidth) {
        StringBuilder renderedRow = new StringBuilder();
        renderedRow.append(WALL);
        for (int i = 0; i < row.size() - 1; i++) {
            String cell = row.get(i);
            int whitespace = columnsWidth[i] - cell.length();
            renderedRow.append(" ".repeat(whitespace)).append(cell);
            renderedRow.append(WALL);
        }
        String cell = row.get(row.size() - 1);
        int whitespace = columnsWidth[row.size() - 1] - cell.length();
        renderedRow.append(" ".repeat(whitespace)).append(cell);
        renderedRow.append("\n");

        return renderedRow.toString();
    }

    @Override
    public String addTableHeader(int[] columnsWidth) {
        StringBuilder header = new StringBuilder();
        int rowLength = Arrays.stream(columnsWidth).sum();

        header.append(WALL)
            .append(ROW.repeat(rowLength + columnsWidth.length - 1))
            .append("\n");

        return header.toString();
    }
}
