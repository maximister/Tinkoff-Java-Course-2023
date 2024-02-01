package edu.hw8.task3;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.steppschuh.markdowngenerator.table.Table;

public class PasswordDecoderReportGenerator {
    private final   Map<String, String> testMap;
    private final int[] treadsAmount = {1, 2, 4, 8};
    private long singleThreadAvgTime;

    public PasswordDecoderReportGenerator() {
        testMap = new HashMap<>();
        testMap.put("f55885d163056122ec9fc8b552658272", "Ivan");
        testMap.put("e5d14f210fc1409098988f66ec84f56f", "Vladislav");
        testMap.put("3ff26733a9cfeb74c2dcbae462ec49fc", "German");
        testMap.put("2e315dcaa77983999bf11106c65229dc", "Sergay");
        testMap.put("b5c0b187fe309af0f4d35982fd961d7e", "Maxim");
        testMap.put("03158cf39c6f316f9ce98a4e034cdc28", "Denchik");
        testMap.put("2030cec40429216c7ce9781e550763e7", "Dora");
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder();
        final String hyphenation = "\n\n";

        for (int threads : treadsAmount) {
            report.append("#### ").append(threads).append(" поток(ов)").append(hyphenation);
            if (threads == 1) {
                report.append(getMarkdownTable(singleThreadBenchmark()));
            } else {
                report.append(
                    getMarkdownTable(
                        runBenchmark(threads)
                    ));
            }

            report.append(hyphenation);
        }

        return report.toString();
    }

    private List<String> singleThreadBenchmark() {
        long start;
        long end;
        BigInteger commonTime = BigInteger.ZERO;
        long avgTime;
        Md5PasswordDecoder decoder;
        final int iterationsAmount = 3;

        for (int i = 0; i < iterationsAmount; i++) {
            decoder = new SingleThreadMd5PasswordDecoder(testMap);
            start = System.nanoTime();
            decoder.decode();
            end = System.nanoTime();
            commonTime = commonTime.add(BigInteger.valueOf(end - start));
        }

        avgTime = commonTime.divide(BigInteger.valueOf(iterationsAmount)).longValue();
        singleThreadAvgTime = avgTime;
        return List.of(Integer.toString(iterationsAmount), Long.toString(avgTime), Integer.toString(1));
    }

    private List<String> runBenchmark(int threads) {
        long start;
        long end;
        BigInteger commonTime = BigInteger.ZERO;
        long avgTime;
        Md5PasswordDecoder decoder;
        final int iterationsAmount = 3;

        for (int i = 0; i < iterationsAmount; i++) {
            decoder = new MultiThreadMd5PasswordDecoder(threads, testMap.size() / threads + 1, testMap);
            start = System.nanoTime();
            decoder.decode();
            end = System.nanoTime();
            commonTime = commonTime.add(BigInteger.valueOf(end - start));
        }

        avgTime = commonTime.divide(BigInteger.valueOf(iterationsAmount)).longValue();
        return List.of(
            Integer.toString(iterationsAmount),
            Long.toString(avgTime),
            Double.toString((double) singleThreadAvgTime / avgTime)
        );
    }

    private String getMarkdownTable(List<String> row) {
        Table.Builder tableBuilder = new Table.Builder()
            .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER, Table.ALIGN_LEFT)
            .addRow("Количество итераций", "Среднее Время (ns)", "Многопоток/Однопоток");

        tableBuilder.addRow(row.get(0), row.get(1), row.get(2));

        return tableBuilder.build().toString();
    }

//    public static void main(String[] args) {
//        PasswordDecoderReportGenerator generator = new PasswordDecoderReportGenerator();
//        System.out.println(generator.generateReport());
//    }
}
