package edu.project4.renderer;

import edu.project4.transformations.Transformation;
import lombok.experimental.UtilityClass;
import java.util.List;

@UtilityClass
public class RendererFactory {
    public static Renderer getRenderer(
        int threadsAmount,
        int affineTransformationsAmount,
        int samples,
        int iterationsPerSample,
        int symmetry,
        List<Transformation> variations
    ) {
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
