package edu.project1;

import edu.project1.input_and_output.Printer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.io.*;

public class PrinterTest {
    @Test
    @DisplayName("Проверка класса Printer")
    public void printerTest() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(os, false, "UTF-8"));
            Printer printer = new Printer();
            printer.printMessage("Hello world!");

            String systemName = System.getProperty("os.name");
            if (systemName.contains("Windows")) {
                assertThat(os.toString("UTF-8")).isEqualTo("-> Hello world!\r\n");
            } else {
                assertThat(os.toString("UTF-8")).isEqualTo("-> Hello world!\n");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
