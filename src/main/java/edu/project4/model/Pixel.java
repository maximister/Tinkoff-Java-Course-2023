package edu.project4.model;

import java.awt.Color;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pixel {
    private int red;
    private int green;
    private int blue;
    private int hitCount;
    private double normal;

    public void setRGB(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public void saturateColor(Color color) {
        if (hitCount == 0) {
            setRGB(color.getRed(), color.getGreen(), color.getBlue());
        } else {
            red = (red + color.getRed()) / 2;
            green = (green + color.getGreen()) / 2;
            blue = (blue + color.getBlue()) / 2;
        }
        hitCount++;
    }

    public static Pixel pointToPixel(Rect world, Point point, FractalImage image) {
        if (!world.contains(point)) {
            return null;
        }
        return image.pixel(
            (int) ((point.x() - world.x()) / world.width() * image.width()),
            (int) ((point.y() - world.y()) / world.height() * image.height())
        );
    }
}
