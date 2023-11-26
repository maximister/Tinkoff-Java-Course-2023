package edu.hw7.task4;

import java.util.ArrayList;
import java.util.List;
import net.steppschuh.markdowngenerator.table.Table;

public class ReportGenerator {
    private final int[] treadsAmount = {1, 2, 4, 8};
    //не нашел, как красиво задать без тайпкаста, либо math pow,
    // либо так, но такая надпись мне кажется нагляднее
    private final long[] iterations = {(long) 1e6, (long) 1e7, (long) 1e8, (long) 1e9};
    private final MonteCarloPiComputer computer;
    private static final int TO_PERCENT = 100;

    public ReportGenerator() {
        computer = new MonteCarloPiComputer();
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        final String hyphenation = "\n\n";

        for (int threads : treadsAmount) {
            report.append("#### ").append(threads).append(" поток(ов)").append(hyphenation);
            report.append(
                getMarkdownTable(
                    runBenchmark(threads)
                ));

            report.append(hyphenation);
        }

        return report.toString();
    }

    private List<List<String>> runBenchmark(int threads) {
        List<List<String>> table = new ArrayList<>();
        long start;
        long end;
        double pi;

        for (long iter : iterations) {
            //количество итераций | время | результат | погрешность
            List<String> row = new ArrayList<>();

            if (threads > 1) {
                start = System.nanoTime();
                pi = computer.computePiInOneThread(iter);
                end = System.nanoTime();
            } else {
                start = System.nanoTime();
                pi = computer.computePiMultiThreading(iter, threads);
                end = System.nanoTime();
            }

            row.add(Long.toString(iter));
            row.add(Long.toString(end - start));
            row.add(Double.toString(pi));
            row.add(Double.toString(getPiCalculationError(pi)));

            table.add(row);
        }

        return table;
    }

    private String getMarkdownTable(List<List<String>> table) {
        //защита от чекстайла, не очень хочется это выносить в константы класса,
        // они там немного мешаются апо моему мнению, а тут хоть сразу понятно, что это
        final int interCol = 0;
        final int timeCol = 1;
        final int resCol = 2;
        final int errCol = 3;

        Table.Builder tableBuilder = new Table.Builder()
            .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER, Table.ALIGN_CENTER, Table.ALIGN_LEFT)
            .addRow("Количество итераций", "Время (ns)", "Результат", "Погрешность");

        for (var row : table) {
            tableBuilder.addRow(row.get(interCol), row.get(timeCol), row.get(resCol), row.get(errCol));
        }

        return tableBuilder.build().toString();
    }

    private double getPiCalculationError(double pi) {
        //не особо понял, какую погрешность от меня хотят в условии,
        // посчитал относительную (что помню с лаб по физике, бррр)
        return Math.abs(Math.PI - pi) * TO_PERCENT;
    }
}
