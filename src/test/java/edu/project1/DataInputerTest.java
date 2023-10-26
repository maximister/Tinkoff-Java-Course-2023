package edu.project1;

import edu.project1.input_and_output.DataInputer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.*;

public class DataInputerTest {
    @Test
    @DisplayName("Проверка работы DataInputer")
    public void dataInputerTest() {
        System.setIn(new ByteArrayInputStream("Cat".getBytes()));

        DataInputer inputer = new DataInputer();
        assertThat(inputer.getInputData()).isEqualTo("cat");
    }
}
