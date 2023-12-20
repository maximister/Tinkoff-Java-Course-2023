package edu.project4.utils;

import edu.project4.ImageCreator;
import edu.project4.imageCreator.ImageFormat;
import edu.project4.imageCreator.ImageUtils;
import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.renderer.RenderSettings;
import edu.project4.renderer.Renderer;
import edu.project4.renderer.RendererFactory;
import edu.project4.transformations.HandkerchiefTransformation;
import edu.project4.transformations.Transformation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImageUtilsTest {

    private final List<ImageFormat> formats = List.of(ImageFormat.PNG, ImageFormat.BMP, ImageFormat.JPEG);

    @SneakyThrows
    @Test
    @DisplayName("testing img saver")
    public void imageUtilsSave_shouldCreateCorrectFile() {
        Path directoryPath = Path.of("src/test/java/edu/project4/utils/");
        FractalImage image = ImageCreator.createImage();

        for (int i = 0; i < 3; i++) {
            Path filePath = Path.of(directoryPath.toString(), "file" + (i+1) + "." + formats.get(i).name());
            ImageUtils.save(image, filePath, formats.get(i));
        }

        assertAll(
            () -> assertTrue(Files.exists(Path.of(directoryPath.toString(), "file1.PNG"))),
            () -> assertTrue(Files.exists(Path.of(directoryPath.toString(), "file2.BMP"))),
            () -> assertTrue(Files.exists(Path.of(directoryPath.toString(), "file3.JPEG")))
        );

        Files.delete(Path.of(directoryPath.toString(), "file1.PNG"));
        Files.delete(Path.of(directoryPath.toString(), "file2.BMP"));
        Files.delete(Path.of(directoryPath.toString(), "file3.JPEG"));
    }
}
