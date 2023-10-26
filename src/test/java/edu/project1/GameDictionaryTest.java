package edu.project1;

import edu.project1.dictionary.GameDictionary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GameDictionaryTest {

    @ParameterizedTest
    @MethodSource("DictionaryTestArgs")
    @DisplayName("Проверка работы класса Dictionary: ввод корректных и некорректных слов, слов с разным регистром")
    public void DictionaryTest(String[] dictionary, String[] correctDictionary) {
        GameDictionary dict = new GameDictionary(dictionary);
        assertThat(dict.getDictionary()).isEqualTo(Arrays.asList(correctDictionary));
    }

    private static Stream<Arguments> DictionaryTestArgs() {
        String[] correctArray = new String[2];
        correctArray[0] = "biden";
        correctArray[1] = "oleg";

        String[] arrayWithInCorrectWords = new String[4];
        arrayWithInCorrectWords[0] = "biden";
        arrayWithInCorrectWords[1] = "oleg";
        arrayWithInCorrectWords[2] = "";
        arrayWithInCorrectWords[3] = "s1eep";

        return Stream.of(
            Arguments.of(correctArray, correctArray),
            Arguments.of(arrayWithInCorrectWords, correctArray)
        );
    }

    @Test
    @DisplayName("Проверка работы конструктора по умолчанию")
    public void costructorTest() {
        GameDictionary dictionary = new GameDictionary();
        ArrayList<String> dict = new ArrayList<>();
        dict.add("miserable");
        dict.add("despair");
        dict.add("astenia");
        dict.add("amerima");
        dict.add("sushi");
        dict.add("blitzkrieg");
        dict.add("tinkoff");
        dict.add("oguzok");

        assertThat(dictionary.getDictionary()).isEqualTo(dict);
    }

    @Test
    @DisplayName("проверка метода isEmpty")
    public void isEmptyTest() {
        String[] array1 = new String[4];
        array1[0] = "joeBuba44";
        array1[1] = "121";
        array1[2] = "";
        array1[3] = "\n";

        String[] array2 = new String[1];
        array2[0] = "joeBuba";

        GameDictionary dictionary = new GameDictionary(array1);
        assertThat(dictionary.isEmpty()).isTrue();

        dictionary = new GameDictionary(array2);
        assertThat(dictionary.isEmpty()).isFalse();
    }

}
