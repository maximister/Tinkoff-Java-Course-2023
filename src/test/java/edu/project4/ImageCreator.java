package edu.project4;

import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.renderer.RenderSettings;
import edu.project4.renderer.Renderer;
import edu.project4.renderer.RendererFactory;
import edu.project4.transformations.HandkerchiefTransformation;
import edu.project4.transformations.Transformation;
import java.util.List;

public class ImageCreator {
    public static FractalImage createImage() {
        double gamma = 2.5;
        int[] resolution = {800, 400};
        double[] aspectRatio = {2.0, 1.0};
        List<Transformation> variations = List.of(new HandkerchiefTransformation());

        RenderSettings settings = RenderSettings.builder()
            .affineTransformationsAmount(10)
            .samples(10)
            .iterationsPerSample(10_000)
            .symmetry(1)
            .variations(variations)
            .threadsAmount(Runtime.getRuntime().availableProcessors())
            .build();

        Renderer renderer = RendererFactory.getRenderer(settings);

        return renderer.render(
            resolution[0],
            resolution[1],
            new Rect(-aspectRatio[0] / 2, -aspectRatio[1] / 2, aspectRatio[0], aspectRatio[1])
        );

    }
}
