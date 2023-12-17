package edu.hw8.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PasswordDecoderTest {
    //по идее работа классов проверялась в процессе создания сравнительной таблицы,
    // так что тут я просто запускаю функции для поднятия ковераге

    @Test
    @DisplayName("Testing single thread decoding")
    public void singleDecodingTest() {
        Md5PasswordDecoder decoder = new SingleThreadMd5PasswordDecoder(getMap());

        assertThat(decoder.getDecodedPasswords()).isEqualTo(Map.of("Ivan", "zolo"));
    }

    @Test
    @DisplayName("Testing multi thread decoding")
    public void multiDecodingTest() {
        Md5PasswordDecoder decoder = new MultiThreadMd5PasswordDecoder(8, 1, getMap());

        assertThat(decoder.getDecodedPasswords()).isEqualTo(Map.of("Ivan", "zolo"));
    }

    @Test
    @DisplayName("Testing report generator")
    public void reportGeneratorTest() throws NoSuchFieldException, IllegalAccessException {
        PasswordDecoderReportGenerator generator = new PasswordDecoderReportGenerator();

        Field threadField = generator.getClass().getDeclaredField("treadsAmount");
        threadField.setAccessible(true);
        int[] newThreads = {1, 8};
        threadField.set(generator, newThreads);

        Field mapField = generator.getClass().getDeclaredField("testMap");
        mapField.setAccessible(true);
        mapField.set(generator, getMap());

        assertDoesNotThrow(generator::generateReport);
    }


    private Map<String, String> getMap() {
        return Map.of("f55885d163056122ec9fc8b552658272", "Ivan");
    }
}
