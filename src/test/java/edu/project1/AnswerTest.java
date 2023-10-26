package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AnswerTest {
    @ParameterizedTest
    @MethodSource("updateAnswerTestArgs")
    @DisplayName("Проверка корректности процесса заполнения слова")
    public void updateAnswerTest(String word, char letter, String expected) {
        Answer answer = new Answer(word);
        answer.updateUserAnswer(letter);

        assertThat(answer.getAnswer()).isEqualTo(word);
        assertThat(answer.getUserAnswer()).isEqualTo(expected);
    }

    private static Stream<Arguments> updateAnswerTestArgs() {
        return Stream.of(
            Arguments.of("dog", 'o', "*o*"),
            Arguments.of("moscow", 'o', "*o**o*"),
            Arguments.of("test", 'd', "****")
        );
    }

    @Test
    @DisplayName("Тест метода isWin")
    public void isWinTest() {
        Answer answer = new Answer("a");
        assertThat(answer.isWin()).isFalse();

        answer.updateUserAnswer('a');
        assertThat(answer.isWin()).isTrue();
    }

    @Test
    @DisplayName("Тест метода isDefeat")
    public void isDefeatTest() {
        Answer answer = new Answer("defeat");

        assertThat(answer.isDefeat(12, 12)).isTrue();
        assertThat(answer.isDefeat(11, 12)).isFalse();
    }
}
