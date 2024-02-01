package edu.project2;

import edu.project2.input_reader.ConsoleReader;
import edu.project2.input_reader.InputReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReaderTest {
    @Test
    @DisplayName("Проверка работы ConsoleReader")
    public void consoleReaderTest() {
        System.setIn(new ByteArrayInputStream("Cat".getBytes()));

        InputReader reader = new ConsoleReader();
        assertThat(reader.getInput()).isEqualTo("Cat");
    }
}
