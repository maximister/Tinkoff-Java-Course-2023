package edu.project4.renderer;

import edu.project4.transformations.Transformation;
import java.util.List;
import lombok.Builder;

@Builder
public record RenderSettings(
    int affineTransformationsAmount,
    int samples,
    int iterationsPerSample,
    int symmetry,
    List<Transformation> variations,
    int threadsAmount) {
}
