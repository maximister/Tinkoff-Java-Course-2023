package edu.hw7.task2;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static edu.hw7.task2.ParallelFactorialCounter.getFactorial;
import static org.assertj.core.api.Assertions.assertThat;

public class ParallelFactorialCounterTest {
    @ParameterizedTest
    @CsvSource(value = {
        "0, 1",
        "1, 1",
        "5, 120"
    })
    @DisplayName("testing parallel factorial")
    public void parallelFactorialCounterTest(long value, String stringExpected) {
        BigInteger expected = new BigInteger(stringExpected);

        assertThat(getFactorial(value)).isEqualTo(expected);
    }

    @Test
    @DisplayName("testing parallel factorial with big value")
    public void parallelFactorialCounterTest() {
        Path serializedFactPath = Path.of("src/test/java/edu/hw7/task2/saveFactorial.ser");
        if (!Files.exists(serializedFactPath)) {
            serializeFactorial100_000();
        }

        try (FileInputStream fileInputStream = new FileInputStream(serializedFactPath.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            BigInteger expected = (BigInteger) objectInputStream.readObject();

            assertThat(getFactorial(100000)).isEqualTo(expected);

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void serializeFactorial100_000() {
        //до этого записывал факториал 100_000 строкой в файл и считывал,
        // но тк большой число преобразуется из строки вооде за n^2,
        // тест был очень долгим, так что попробую поиграть с сериализацией
        // и выполнить этот долгий процесс лишь раз

        // P.S прирост в скорости в 5 раз, с учетом того,
        // что встроеные средства вроде не самые быстрые, я доволен

        try (FileOutputStream outputStream = new FileOutputStream("src/test/java/edu/hw7/task2/saveFactorial.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
             InputStream fis = new FileInputStream("src/test/java/edu/hw7/task2/longFactorial.txt")){

                String longString = IOUtils.toString(fis, StandardCharsets.UTF_8);
                BigInteger factorial = new BigInteger(longString.trim());
                objectOutputStream.writeObject(factorial);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
