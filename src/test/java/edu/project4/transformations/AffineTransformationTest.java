package edu.project4.transformations;

import edu.project4.model.Point;
import edu.project4.transformations.affineTransformation.AffineCoefficient;
import edu.project4.transformations.affineTransformation.AffineCoefficientGenerator;
import edu.project4.transformations.affineTransformation.AffineTransformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AffineTransformationTest {

    @Test
    @DisplayName("testing that affine coefficient is affine")
    public void affineCoefficientGeneratorTest() {
        AffineCoefficient testCoefficient = AffineCoefficientGenerator.generate();

        assertTrue(isAffine(testCoefficient));
    }

    @Test
    @DisplayName("testing affine transformation function")
    public void affineTransformationTest() {
        AffineCoefficient testCoefficient =
            new AffineCoefficient(0.41, 0.56, -0.48, -0.779, 0.24, -0.23, Color.black);

        assertEquals(
            MathTransformationTest.applyTransformation(new AffineTransformation(testCoefficient)),
            new Point(453000, 39900)
        );
    }

    private boolean isAffine(AffineCoefficient coefficient) {
        return ((coefficient.a() * coefficient.a() + coefficient.d() * coefficient.d()) < 1)
            && ((coefficient.b() * coefficient.b() + coefficient.e() * coefficient.e()) < 1)
            && ((coefficient.a() * coefficient.a() + coefficient.b() * coefficient.b() +
            coefficient.d() * coefficient.d() + coefficient.e() * coefficient.e()) <
            (1 + (coefficient.a() * coefficient.e() - coefficient.b() * coefficient.d()) *
                (coefficient.a() * coefficient.e() - coefficient.b() * coefficient.d())));
    }
}
