package edu.project4.imageProcessor;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;

public class LogGammaCorrectorProcessor implements ImageProcessor {
    private final double gamma;

    public LogGammaCorrectorProcessor(double gamma) {
        if (gamma > 0) {
            this.gamma = gamma;
        } else {
            throw new IllegalArgumentException("Gamma value should be positive number");
        }
    }

    @Override
    public void process(FractalImage image) {
        double max = 0.0;
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                if (image.pixel(x, y).getHitCount() != 0) {
                    image.pixel(x, y).setNormal(Math.log10(image.pixel(x, y).getHitCount()));
                    if (image.pixel(x, y).getNormal() > max) {
                        max = image.pixel(x, y).getNormal();
                    }
                }
            }
        }

        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.pixel(x, y);
                pixel.setNormal(image.pixel(x, y).getNormal() / max);
                pixel.setRGB((int) (pixel.getRed() * Math.pow(pixel.getNormal(), (1.0 / gamma))),
                (int) (pixel.getGreen() * Math.pow(pixel.getNormal(), (1.0 / gamma))),
                (int) (pixel.getBlue() * Math.pow(pixel.getNormal(), (1.0 / gamma))));
            }
        }

    }
}
