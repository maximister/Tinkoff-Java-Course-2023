package edu.project4.imageProcessor;

import edu.project4.model.FractalImage;

@FunctionalInterface
public
interface ImageProcessor {
    void process(FractalImage image);
}
