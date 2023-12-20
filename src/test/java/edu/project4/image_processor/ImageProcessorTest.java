package edu.project4.image_processor;

import edu.project4.ImageCreator;
import edu.project4.imageProcessor.ImageProcessor;
import edu.project4.imageProcessor.LogGammaCorrectorProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImageProcessorTest {
    @Test
    @DisplayName("testing log gamma corrector work with correct gamma parameter")
    public void logGammaCorrector_shouldCorrectlyWork() {
        ImageProcessor processor = new LogGammaCorrectorProcessor(2.2);

        assertDoesNotThrow(() -> processor.process(ImageCreator.createImage()));
    }

    @Test
    @DisplayName("testing log gamma corrector work with invalid parameters")
    public void logGammaCorrector_shouldThrowException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new LogGammaCorrectorProcessor(0)
        );
    }
}
