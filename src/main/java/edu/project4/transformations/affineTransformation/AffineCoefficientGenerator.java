package edu.project4.transformations.affineTransformation;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public record AffineCoefficientGenerator() {
    private static final int MAX_RGB_VALUE = 256;

    public static AffineCoefficient generate() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double a;
        double b;
        double d;
        double e;
        double c = random.nextDouble(-1, 1);
        double f = random.nextDouble(-1, 1);

        do {
            a = random.nextDouble(-1, 1);
            b = random.nextDouble(-1, 1);
            d = random.nextDouble(-1, 1);
            e = random.nextDouble(-1, 1);

        } while (!isAffine(a, b, d, e));
        return new AffineCoefficient(
            a, b, c, d, e, f,
            new Color(random.nextInt(MAX_RGB_VALUE),
                random.nextInt(MAX_RGB_VALUE),
                random.nextInt(MAX_RGB_VALUE))
        );
    }

    private static boolean isAffine(double a, double b, double d, double e) {
        return ((a * a + d * d) < 1)
            && ((b * b + e * e) < 1)
            && ((a * a + b * b + d * d + e * e) < (1 + (a * e - b * d) * (a * e - b * d)));
    }
}
