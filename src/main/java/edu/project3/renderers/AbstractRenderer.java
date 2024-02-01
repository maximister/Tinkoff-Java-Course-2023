package edu.project3.renderers;

import edu.project3.metrics.MetricsContainer;
import edu.project3.metrics.MetricsRow;
import java.util.List;

public abstract class AbstractRenderer implements TableRenderer {

    protected int[] computeColumnsWidth(MetricsContainer table, int cols) {
        List<MetricsRow> rows = table.getTableRows();
        int[] columnsWidth = new int[cols];

        rows.forEach(row -> {
            List<String> currentCols = row.values();
            for (int i = 0; i < cols; i++) {
                if (currentCols.get(i).length() > columnsWidth[i]) {
                    columnsWidth[i] = currentCols.get(i).length();
                }
            }
        });

        return columnsWidth;
    }

    public abstract String addHeader(String header);

    protected abstract String addRow(List<String> row, int[] columnsWidth);

    public abstract String addTableHeader(int[] columnsWidth);
}
