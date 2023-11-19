package edu.project3.metrics;

import java.util.List;
import lombok.Builder;

@Builder
public record MetricsRow(List<String> values) {
    public boolean isValid(int cols) {
        return cols == values.size();
    }
}
