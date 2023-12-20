package edu.project4.renderer;

import edu.project4.transformations.DiskTransformation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RenderersTest {
    //вот тут я не придумал, как протестировать рендеры, поэтому только запущу и проверю на ошибки
    //буду рад подсказкам по тестированию

    @ParameterizedTest
    @MethodSource("getInvalidSettings")
    @DisplayName("testing renderer with invalid parameters")
    public void abstractRenderer_shouldThrowExceptions(RenderSettings settings) {
        assertThrows(
            IllegalArgumentException.class,
            () -> new SingleThreadRenderer(
                settings.affineTransformationsAmount(),
                settings.samples(),
                settings.iterationsPerSample(),
                settings.symmetry(),
                settings.variations()
            )
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> new MultiThreadRenderer(
                settings.affineTransformationsAmount(),
                settings.samples(),
                settings.iterationsPerSample(),
                settings.symmetry(),
                settings.variations(),
                settings.threadsAmount()
            )
        );
    }

    @Test
    @DisplayName("testing renderers with correct parameters")
    public void renderers_shouldNotThrowExceptions() {
        RenderSettings settings = getDefaultSettings();

        assertDoesNotThrow(() -> new SingleThreadRenderer(
            settings.affineTransformationsAmount(),
            settings.samples(),
            settings.iterationsPerSample(),
            settings.symmetry(),
            settings.variations()
        ));

        assertDoesNotThrow(() -> new MultiThreadRenderer(
            settings.affineTransformationsAmount(),
            settings.samples(),
            settings.iterationsPerSample(),
            settings.symmetry(),
            settings.variations(),
            2
        ));
    }

    @Test
    @DisplayName("testing renderer factory")
    public void rendererFactoryTest() {
        RenderSettings settings = getDefaultSettings();

        Renderer singleThreadRenderer = RendererFactory.getRenderer(1,
            settings.affineTransformationsAmount(),
            settings.samples(),
            settings.iterationsPerSample(),
            settings.symmetry(),
            settings.variations()
        );
        Renderer multiTreadRenderer = RendererFactory.getRenderer(5,
            settings.affineTransformationsAmount(),
            settings.samples(),
            settings.iterationsPerSample(),
            settings.symmetry(),
            settings.variations()
        );

        assertTrue(singleThreadRenderer instanceof SingleThreadRenderer);
        assertTrue(multiTreadRenderer instanceof MultiThreadRenderer);

        assertThrows(IllegalArgumentException.class,
            () -> RendererFactory.getRenderer(0,
                settings.affineTransformationsAmount(),
                settings.samples(),
                settings.iterationsPerSample(),
                settings.symmetry(),
                settings.variations()
            ));

    }

    private static Stream<Arguments> getInvalidSettings() {
        return Stream.of(
            Arguments.of(RenderSettings.builder()
                .affineTransformationsAmount(0)
                .samples(1)
                .iterationsPerSample(1)
                .symmetry(1)
                .variations(List.of(new DiskTransformation()))
                .threadsAmount(1)
                .build()),
            Arguments.of(RenderSettings.builder()
                .affineTransformationsAmount(1)
                .samples(0)
                .iterationsPerSample(1)
                .symmetry(1)
                .variations(List.of(new DiskTransformation()))
                .threadsAmount(1)
                .build()),
            Arguments.of(RenderSettings.builder()
                .affineTransformationsAmount(1)
                .samples(1)
                .iterationsPerSample(0)
                .symmetry(1)
                .variations(List.of(new DiskTransformation()))
                .threadsAmount(1)
                .build()),
            Arguments.of(RenderSettings.builder()
                .affineTransformationsAmount(1)
                .samples(1)
                .iterationsPerSample(1)
                .symmetry(0)
                .variations(List.of(new DiskTransformation()))
                .threadsAmount(1)
                .build()),
            Arguments.of(RenderSettings.builder()
                .affineTransformationsAmount(1)
                .samples(1)
                .iterationsPerSample(1)
                .symmetry(1)
                .variations(Collections.emptyList())
                .threadsAmount(1)
                .build())
        );
    }

    public static RenderSettings getDefaultSettings() {
        return RenderSettings.builder()
            .affineTransformationsAmount(1)
            .iterationsPerSample(1)
            .samples(1)
            .variations(List.of(new DiskTransformation()))
            .symmetry(1)
            .threadsAmount(1)
            .build();
    }
}
