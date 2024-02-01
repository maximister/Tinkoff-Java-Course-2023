package edu.project3.metrics;

import edu.project3.statistic_collectors.StatisticsCollector;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

//что-то вроде хранилища для будущей таблицы
@Getter
public abstract class MetricsContainer {
    //название таблицы
    @Getter protected String header;
    protected List<MetricsRow> table = new ArrayList<>();
    @Getter protected List<StatisticsCollector> collectors;
    protected int rows;
    protected int cols;

    public abstract void build();

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void addRows(List<MetricsRow> rows) {
        rows.forEach(this::addRow);
    }

    public void addRow(MetricsRow row) {
        table.add(row);
    }

    public List<MetricsRow> getTableRows() {
        return table;
    }
}
