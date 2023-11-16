package edu.hw5.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw5.task5.CarNumberValidator.isValidNumber;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarNumberValidatorTest {
    @ParameterizedTest
    @CsvSource(value = {
        "В004КО198, true",
        "А123ВЕ777, true",
        "О777ОО177, true",
        "123АВЕ777, false",
        "А123ВГ77, false",
        "А123ВЕ7777, false",
        "0777ОО177, false"
    })
    @DisplayName("isValidNumber method test")
    public void isValidNumberTest(String value, boolean expected) {
        assertThat(isValidNumber(value)).isEqualTo(expected);
    }
}
