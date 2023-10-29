package edu.hw2.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw2.task4.CallingInfo.callingInfo;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CallingInfoTest {

    @Test
    @DisplayName("Проверка того, что метод выводит последний вызванный метод (исключая себя)")
    public void callingInfoTest() {
        CallingInfo res = callingInfo();
        CallingInfo expected = new CallingInfo("edu.hw2.task4.CallingInfoTest", "callingInfoTest");
        assertThat(res).isEqualTo(expected);
    }
}
