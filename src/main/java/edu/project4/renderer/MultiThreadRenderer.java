package edu.project4.renderer;

import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.transformations.Transformation;
import edu.project4.transformations.affineTransformation.AffineTransformation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadRenderer extends AbstractRenderer {
    private final int threadsAmount;

    public MultiThreadRenderer(
        int affineTransformationsAmount,
        int samples,
        int iterationsPerSample,
        int symmetry,
        List<Transformation> variations,
        int threadsAmount
    ) {
        super(affineTransformationsAmount, samples, iterationsPerSample, symmetry, variations);
        this.threadsAmount = threadsAmount;
    }

    @Override
    protected void renderImage(FractalImage fractalImage, Rect world, List<AffineTransformation> transformations) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(threadsAmount)) {
            for (int i = 0; i < samples; i++) {
                executorService.execute(
                    () -> processOneSample(fractalImage, world, transformations)
                );
            }
            executorService.shutdown();
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
