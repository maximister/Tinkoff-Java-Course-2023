package edu.project3.renderers;

import edu.project3.metrics.MetricsContainer;

public interface TableRenderer {
    String getRenderedTable(MetricsContainer table, int cols);
}
