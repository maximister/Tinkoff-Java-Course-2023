package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SessionTest {
    @ParameterizedTest
    @MethodSource("guessTestArgs")
    @DisplayName("Тестирование guess")
    public void guessTest(GuessResult result, Answer answer, int maxAttempts, char guess) {
        Session session = new Session(answer, maxAttempts);
        GuessResult sessionResult = session.guess(guess);

        assertThat(sessionResult).isEqualTo(result);
    }

    private static Stream<Arguments> guessTestArgs() {
        Answer answer = new Answer("ab");
        Answer winAnswer = new Answer("ab");
        winAnswer.updateUserAnswer('a');

        return Stream.of(
            Arguments.of(
                Named.of("Промах",
                    new GuessResult.FailedGuess(answer, 1, 3)), answer, 3, 'c'),
            Arguments.of(
                Named.of("Поражение",
                    new GuessResult.Defeat(answer, 1, 1)), answer, 1, 'c'),
            Arguments.of(
                Named.of("Победа",
                    new GuessResult.Win(winAnswer, 0, 3)), winAnswer,  3, 'b'),
            Arguments.of(
                Named.of("Попадание",
                    new GuessResult.SuccessfulGuess(answer, 0, 3)), answer, 3, 'b')
        );
    }

    @Test
    @DisplayName("Тестирование guess - повтор")
    public void guessRepeatCaseTest() {
        Answer answer = new Answer("ab");
        Session session = new Session(answer, 4);
        session.guess('a');

        GuessResult result = session.guess('a');
        GuessResult.Repeat expected = new GuessResult.Repeat(answer, 0, 4);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Тестирование метода giveUp")
    public void giveUpTest() {
        Answer answer = new Answer("ab");
        Session session = new Session(answer, 4);

        GuessResult.Defeat expected = new GuessResult.Defeat(answer, 0, 4);

        assertThat(session.giveUp()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getAnswerTestArgs")
    @DisplayName("Тестирование метода getAnswer")
    public void getAnswerTest(Answer answer, String result) {
        Session session = new Session(answer, 4);

        assertThat(session.getAnswer()).isEqualTo(result);

    }

    private static Stream<Arguments> getAnswerTestArgs() {
        Answer winAnswer = new Answer("a");
        winAnswer.updateUserAnswer('a');
        Answer nonWinAnswer = new Answer("a");

        return Stream.of(
            Arguments.of(winAnswer, "The word: " +  "a"),
            Arguments.of(nonWinAnswer, "The word: " + "*")
        );
    }
}
