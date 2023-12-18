package edu.hw11.task1;

import lombok.SneakyThrows;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw11.task1.HelloWorldSpeakerGenerator.getUnloadedClass;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteCodeGeneratedHelloWorldTest {
    @SneakyThrows
    @Test
    @DisplayName("testing dynamically generated hello world speaker")
    public void getUnloadedClass_shouldReturnCorrectUnloadClass() {
        //капец оно долго работает...
        DynamicType.Unloaded unloaded = getUnloadedClass();

        Class<?> dynamicType = unloaded
            .load(getClass().getClassLoader())
            .getLoaded();

        assertEquals(
            dynamicType.getDeclaredConstructor().newInstance().toString(),
            "Welcome to the club, buddy!");
    }
}
