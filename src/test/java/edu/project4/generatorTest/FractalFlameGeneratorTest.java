package edu.project4.generatorTest;

import edu.project4.FractalFlameGenerator;
import edu.project4.FractalFlameReportGenerator;
import edu.project4.imageCreator.ImageFormat;
import edu.project4.renderer.RenderSettings;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import static edu.project4.renderer.RenderersTest.getDefaultSettings;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class FractalFlameGeneratorTest {
    @Test
    @DisplayName("testing that fractal flame generator does not throw exceptions")
    public void fractalFlameGeneratorTest() {
        Path storageDirectory = Path.of("src/test/java/edu/project4/");
        double[] aspectRatio = {1.0, 1.0};
        int[] resolution = {800, 500};


        assertDoesNotThrow(() -> {
            FractalFlameGenerator generator =
                new FractalFlameGenerator(getDefaultSettings(), 1.5, storageDirectory);
            generator.run(aspectRatio, resolution, "test", ImageFormat.PNG);

            Files.delete(Path.of(storageDirectory.toString(), "test.PNG"));
        });
    }

    //генератор отчетов пока тестировать бессмысленно, тк
    //1) он в целом к проету не относится особо и нужен для удобного вывода сравнения производительности
    //2) исходя из 1 пункта я в спешке не особо старался над архитектурой, поэтому там лютый хардкод,
    //который проблематично исправить в тесте
    //надо будет переделать немного под возможность задавать настройки извне, и тогда можно тестик сделать)
}
