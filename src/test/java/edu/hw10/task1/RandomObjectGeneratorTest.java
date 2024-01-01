package edu.hw10.task1;

import edu.hw10.task1.fieldGenerators.AnnotatedParameterGenerator;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.security.SecureRandom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class RandomObjectGeneratorTest {
    @Test
    @DisplayName("Generating record by its constructor")
    public void  randomObjectGenerator_shouldCreateRecordByItsConstructor() {
        RandomObjectGenerator
            rog = new RandomObjectGenerator(new AnnotatedParameterGenerator(new SecureRandom()));
        var testRecord = rog.nextObject(SomeRecordForTest.class);
        assertAll(
            () -> assertThat(testRecord.value()).isGreaterThan(7),
            () -> assertThat(testRecord.value()).isLessThan(13),
            () -> assertThat(testRecord.doubleValue()).isCloseTo(5.0, Offset.offset(1.0)),
            () -> assertThat(testRecord.floatValue()).isLessThan(12),
            () -> assertThat(testRecord.shortValue()).isGreaterThan((short) 5),
            () -> assertThat(testRecord.byteValue()).isLessThan((byte) 24)
        );
    }

    @Test
    @DisplayName("Generating pojo by its constructor")
    public void randomObjectGenerator_shouldCreatePojoByItsConstructor() {
        RandomObjectGenerator rog = new RandomObjectGenerator(new AnnotatedParameterGenerator(new SecureRandom()));
        var testClass = rog.nextObject(BillyHerringtonTestClass.class);
        assertAll(
            () -> assertThat(testClass.getName()).isNotNull(),
            () -> assertThat(testClass.getPrice()).isGreaterThan(299)
        );
    }

    @Test
    @DisplayName("Generating pojo by its factory method")
    public void randomObjectGenerator_shouldCreatePojoByItsFactoryMethod() {
        RandomObjectGenerator rog = new RandomObjectGenerator(new AnnotatedParameterGenerator(new SecureRandom()));
        var testClass = rog.nextObject(BillyHerringtonTestClass.class, "create");
        assertAll(
            () -> assertThat(testClass.getName()).isNotNull(),
            () -> assertThat(testClass.getByceps()).isLessThan(80),
            () -> assertThat(testClass.getPrice()).isGreaterThan(299)
        );
    }
}
