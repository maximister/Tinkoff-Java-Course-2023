package edu.project4.renderer;

import edu.project4.transformations.Transformation;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RendererFactory {
    public static Renderer getRenderer(
        RenderSettings settings
    ) {
        int threadsAmount = settings.threadsAmount();
        int affineTransformationsAmount = settings.affineTransformationsAmount();
        int samples = settings.samples();
        int iterationsPerSample = settings.iterationsPerSample();
        int symmetry = settings.symmetry();
        List<Transformation> variations = settings.variations();

        if (threadsAmount == 1) {
            return new SingleThreadRenderer(
                affineTransformationsAmount,
                samples,
                iterationsPerSample,
                symmetry,
                variations
            );
        } else if (threadsAmount > 1) {
            return new MultiThreadRenderer(
                affineTransformationsAmount,
                samples,
                iterationsPerSample,
                symmetry,
                variations,
                threadsAmount
            );
        }

        throw new IllegalArgumentException("Invalid threads amount");
    }
}
