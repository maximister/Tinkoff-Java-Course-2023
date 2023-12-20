package edu.project4.renderer;

import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.transformations.Transformation;
import edu.project4.transformations.affineTransformation.AffineTransformation;
import java.util.List;

public class SingleThreadRenderer extends AbstractRenderer {
    public SingleThreadRenderer(
        int affineTransformationsAmount,
        int samples,
        int iterationsPerSample,
        int symmetry,
        List<Transformation> variations
    ) {
        super(affineTransformationsAmount, samples, iterationsPerSample, symmetry, variations);
    }

    @Override
    protected void renderImage(FractalImage fractalImage, Rect world, List<AffineTransformation> transformations) {
        for (int i = 0; i < samples; i++) {
            processOneSample(fractalImage, world, transformations);
        }
    }
}
