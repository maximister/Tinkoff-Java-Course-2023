package edu.hw5.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw5.task6.SubstringChecker.isSubstring;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SubstringCheckerTest {
    @ParameterizedTest
    @CsvSource(value = {
        "abc, achfdbaabgabcaabg, true",
        "джаба, жабаджабажаважижа, true",
        "doradura, shefAndOguzok, false",
    })
    @DisplayName("isSubstring method test")
    public void isSubstringTest(String substring, String origin, boolean expected) {
        assertThat(isSubstring(origin, substring)).isEqualTo(expected);
    }
}
