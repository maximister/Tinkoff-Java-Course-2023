package edu.project4.renderer;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.Point;
import edu.project4.model.Rect;
import edu.project4.transformations.Transformation;
import edu.project4.transformations.affineTransformation.AffineCoefficientGenerator;
import edu.project4.transformations.affineTransformation.AffineTransformation;
import edu.project4.utils.FractalFrameUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRenderer implements Renderer {
    //почему 20 - загадка дыры, так сказали умные люди в статье по ссылке)
    private static final int STEPS_FOR_NORMALIZATION = 20;

    protected final int affineTransformationsAmount;
    protected final int samples;
    protected final int iterationsPerSample;
    //количество осей симметрии - 1; значение 1 - нет симметрии
    protected final int symmetry;
    protected final List<Transformation> variations;

    public AbstractRenderer(
        int affineTransformationsAmount,
        int samples,
        int iterationsPerSample,
        int symmetry,
        List<Transformation> variations
    ) {
        if (affineTransformationsAmount <= 0 || samples <= 0
            || iterationsPerSample <= 0 || symmetry <= 0 || variations.isEmpty()) {
            throw new IllegalArgumentException("invalid renderer parameters");
        }

        this.affineTransformationsAmount = affineTransformationsAmount;
        this.samples = samples;
        this.iterationsPerSample = iterationsPerSample;
        this.symmetry = symmetry;
        this.variations = variations;
    }

    @Override
    public FractalImage render(int width, int height, Rect world) {
        FractalImage fractalImage = FractalImage.create(width, height);
        List<AffineTransformation> transformations = getAffineTransformationList();
        renderImage(fractalImage, world, transformations);

        return fractalImage;
    }

    protected abstract void renderImage(
        FractalImage fractalImage,
        Rect world,
        List<AffineTransformation> transformations
    );

    protected void processOneSample(FractalImage image, Rect world, List<AffineTransformation> affineTransformations) {
        Point currentPoint = FractalFrameUtils.getRandomPoint(world);

        for (int step = -STEPS_FOR_NORMALIZATION; step < iterationsPerSample; step++) {
            AffineTransformation affineTransformation =
                FractalFrameUtils.getRandomListElement(affineTransformations);
            Transformation transformation = FractalFrameUtils.getRandomListElement(variations);

            currentPoint = affineTransformation.apply(currentPoint);
            currentPoint = transformation.apply(currentPoint);

            if (step > 0) {
                double theta = 0.0;
                for (int s = 0; s < symmetry; theta += Math.PI * 2 / symmetry, s++) {
                    Point rotatedPoint = FractalFrameUtils.rotatePoint(world, currentPoint, theta);

                    Pixel pixel = Pixel.pointToPixel(world, rotatedPoint, image);
                    if (pixel != null) {
                        synchronized (pixel) {
                            Color color = affineTransformation.affineCoefficient().color();
                            pixel.saturateColor(color);
                        }
                    }
                }
            }
        }
    }

    private List<AffineTransformation> getAffineTransformationList() {
        List<AffineTransformation> affineTransformationList = new ArrayList<>();
        for (int i = 0; i < affineTransformationsAmount; i++) {
            affineTransformationList.add(
                new AffineTransformation(
                    AffineCoefficientGenerator.generate()
                )
            );
        }

        return affineTransformationList;
    }
}
