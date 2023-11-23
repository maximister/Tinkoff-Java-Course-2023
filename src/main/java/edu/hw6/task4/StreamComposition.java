package edu.hw6.task4;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class StreamComposition {
    private static final String MESSAGE =
        "Programming is learned by writing programs. ― Brian Kernighan\n"
            + "PYTHON ETO EASY BIM BIM BAM BAM. ― python_is_trash";
    private static final Logger LOGGER = LogManager.getLogger();

    private StreamComposition() {
    }

    public static void compose() {
        compose("bimbim.txt", MESSAGE);
    }

    public static void compose(String fileName, String message) {
        try (OutputStream os = Files.newOutputStream(Path.of(fileName));
             CheckedOutputStream checkedOutputStream = new CheckedOutputStream(os, new Adler32());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                 bufferedOutputStream,
                 StandardCharsets.UTF_8.newEncoder()
             );
             PrintWriter printWriter = new PrintWriter(outputStreamWriter)) {

            printWriter.write(message);

        } catch (IOException e) {
            LOGGER.info("Java is not easy blin");
            throw new RuntimeException(e);
        }
    }


}
