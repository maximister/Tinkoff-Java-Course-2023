package edu.hw5.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.stream.Stream;
import static edu.hw5.task2.Friday13Seeker.findAllFriday13;
import static edu.hw5.task2.Friday13Seeker.getNextFriday13;
import static edu.hw5.task4.PasswordValidator.isValidPassword;
import static edu.hw5.task4.PasswordValidator.isValidPasswordVersion2;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordValidatorTest {
    @ParameterizedTest
    @CsvSource(value = {
        "IvanZolo2004, false",
        "Iv@nZolo2004, true",
        "sh@ylu$$ay, true"
    })
    @DisplayName("isValidPassword version 1 test")
    public void isValidPasswordTest(String password, boolean expected) {
        assertThat(isValidPassword(password)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
        "IvanZolo2004, false",
        "Iv@nZolo2004, true",
        "sh@ylu$$ay, false",
        "sh@ylu$ay, false",
    })
    @DisplayName("isValidPassword version 2 test")
    public void isValidPasswordVersion2Test(String password, boolean expected) {
        assertThat(isValidPasswordVersion2(password)).isEqualTo(expected);
    }
}
