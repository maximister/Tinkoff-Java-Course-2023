package edu.hw5.task6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static edu.hw5.task6.SubsequenceChecker.isSubsequence;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SubsequenceCheckerTest {
    @ParameterizedTest
    @CsvSource(value = {
        "abc, achfdbaabgabcaabg, true",
        "avb, angfbbvgrgdfdbvdffd, true",
        "avb, vngfbbagrgdfdbvdffd, false"
    })
    @DisplayName("isSubsequence method test")
    public void isSubsequenceTest(String substring, String origin, boolean expected) {
        assertThat(isSubsequence(origin, substring)).isEqualTo(expected);
    }
}
