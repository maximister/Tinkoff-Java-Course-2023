package edu.project4;

import edu.FractalFlameGenerator;
import edu.project4.imageCreator.ImageFormat;
import edu.project4.renderer.RenderSettings;
import edu.project4.transformations.DiskTransformation;
import edu.project4.transformations.HandkerchiefTransformation;
import edu.project4.transformations.HeartTransformation;
import edu.project4.transformations.HyperbolicTransformation;
import edu.project4.transformations.LinearTransformation;
import edu.project4.transformations.SinusoidalTransformation;
import edu.project4.transformations.SphericalTransformation;
import edu.project4.transformations.SwirlTransformation;
import edu.project4.transformations.Transformation;
import net.steppschuh.markdowngenerator.table.Table;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FractalFlameReportGenerator {
    private final double gamma = 0.9;
    private final int[] resolution = {2048, 1152};
    private final double[] aspectRatio = {9.0, 9.0};
    double avgSpeedResult;
    private final Path storageDir = Path.of("src/main/java/edu/project4/images/");

    public FractalFlameReportGenerator() {
    }

    // однопоток многопоток отношение
    //среднее время
    public String getReport() {
        StringBuilder report = new StringBuilder();
        final String hyphenation = "\n\n";
        final String header = "#### ";

        report.append(
            getMarkdownTable(
                runBenchmark()
            ));

        report.append(hyphenation);
        report.append(header).append("Среднее отношение однопоток/многопоток: ")
            .append(avgSpeedResult);

        return report.toString();
}

    @SuppressWarnings("checkstyle:MagicNumber")
    private String getMarkdownTable(List<List<String>> table) {

        Table.Builder tableBuilder = new Table.Builder()
            .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_CENTER, Table.ALIGN_CENTER, Table.ALIGN_LEFT)
            .addRow(
                "Количество осей симметрии",
                "Время (однопоток), ns",
                "Время (многопоток), ns",
                "Однопоток/многопоток"
            );

        for (var row : table) {
            tableBuilder.addRow(row.get(0), row.get(1), row.get(2), row.get(3));
        }

        return tableBuilder.build().toString();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private List<List<String>> runBenchmark() {
        avgSpeedResult = 0;
        List<List<String>> report = new ArrayList<>();

        for (int symmetry = 1; symmetry <= 5; symmetry++) {
            report.add(runSingleTest(symmetry));
        }

        double avgSingleTime = 0;
        double avgMultiTime = 0;
        for (var row : report) {
            avgSingleTime += Double.parseDouble(row.get(1));
            avgMultiTime += Double.parseDouble(row.get(2));
        }

        avgSpeedResult = avgSingleTime / avgMultiTime;
        return report;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private List<String> runSingleTest(int symmetry) {
        FractalFlameGenerator singleThread = new FractalFlameGenerator(
            getTestSettings(1, symmetry),
            gamma,
            storageDir
        );
        FractalFlameGenerator multiThread = new FractalFlameGenerator(
            getTestSettings(8, symmetry),
            gamma,
            storageDir
        );

        long singleRes;
        long multiRes;
        long start;
        long end;

        start = System.nanoTime();
        singleThread.run(aspectRatio, resolution, getFilename(1, symmetry), ImageFormat.PNG);
        end = System.nanoTime();
        singleRes = end - start;

        start = System.nanoTime();
        multiThread.run(aspectRatio, resolution, getFilename(8, symmetry), ImageFormat.PNG);
        end = System.nanoTime();
        multiRes = end - start;

        return List.of(
            Integer.toString(symmetry),
            Long.toString(singleRes),
            Long.toString(multiRes),
            Double.toString((double) singleRes / multiRes)
        );
    }

    private String getFilename(int threadAmount, int symmetry) {
        String threadName = threadAmount == 1 ? "singleThread" : "multiThread";
        return Integer.toString(symmetry) + "_symmetry_axes_" + threadName;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private RenderSettings getTestSettings(int threadAmount, int symmetry) {
        return RenderSettings.builder()
            .affineTransformationsAmount(10)
            .samples(10)
            .iterationsPerSample(1_000_000)
            .symmetry(symmetry)
            .variations(getDefaultTransformations())
            .threadsAmount(threadAmount)
            .build();
    }

    private List<Transformation> getDefaultTransformations() {
        return List.of(
            new DiskTransformation(),
            new HandkerchiefTransformation(),
            new HeartTransformation(),
            new HyperbolicTransformation(),
            new LinearTransformation(),
            new SinusoidalTransformation(),
            new SphericalTransformation(),
            new SwirlTransformation()
        );
    }

//    public static void main(String[] args) {
//        FractalFlameReportGenerator reportGenerator = new FractalFlameReportGenerator();
//        System.out.println(reportGenerator.getReport());
//    }
}
