package edu.project4;

import edu.project4.imageCreator.ImageFormat;
import edu.project4.imageCreator.ImageUtils;
import edu.project4.imageProcessor.ImageProcessor;
import edu.project4.imageProcessor.LogGammaCorrectorProcessor;
import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.renderer.RenderSettings;
import edu.project4.renderer.Renderer;
import edu.project4.renderer.RendererFactory;
import java.nio.file.Path;

public class FractalFlameGenerator {

    private final ImageProcessor processor;
    private final Renderer renderer;
    private final Path storageDirectory;

    public FractalFlameGenerator(RenderSettings settings, double gamma, Path storageDirectory) {
        renderer = RendererFactory.getRenderer(
            settings.threadsAmount(),
            settings.affineTransformationsAmount(),
            settings.samples(),
            settings.iterationsPerSample(),
            settings.symmetry(),
            settings.variations()
        );

        //тут небольшой хардкод, тк процессор всего один,
        //но при увеличении количества можно сделать также, как с рендером
        processor = new LogGammaCorrectorProcessor(gamma);

        this.storageDirectory = storageDirectory;
    }

    public void run(double[] aspectRatio, int[] resolution, String filename, ImageFormat format) {
        FractalImage fractalImage = renderer.render(
            resolution[0],
            resolution[1],
            new Rect(-aspectRatio[0] / 2, -aspectRatio[1] / 2, aspectRatio[0], aspectRatio[1])
        );
        processor.process(fractalImage);

        Path imgPath = Path.of(storageDirectory.toString(), filename + "." + format.name());

        ImageUtils.save(fractalImage, imgPath, ImageFormat.PNG);
    }

//    @SuppressWarnings({"checkstyle:UncommentedMain", "checkstyle:MagicNumber"})
//    public static void main(String[] args) {
//        double gamma = 0.7;
//        int[] resolution = {2048, 1152};
//        double[] aspectRatio = {9.0, 9.0};
//        Path directoryPath = Path.of("src/main/java/edu/project4/images/");
//        List<Transformation> variations = List.of(
//            new DiskTransformation(),
//            new HandkerchiefTransformation(),
//            new HeartTransformation(),
//            new HyperbolicTransformation(),
//            new LinearTransformation(),
//            new SinusoidalTransformation(),
//            new SphericalTransformation(),
//            new SwirlTransformation()
//        );
//
//        RenderSettings settings = RenderSettings.builder()
//            .affineTransformationsAmount(10)
//            .samples(20)
//            .iterationsPerSample(1_000_000)
//            .symmetry(2)
//            .variations(variations)
//            .threadsAmount(8)
//            .build();
//
//        FractalFlameGenerator generator = new FractalFlameGenerator(settings, gamma, directoryPath);
//        generator.run(aspectRatio, resolution, "test6", ImageFormat.PNG);
//    }
}
