package edu.hw11.task1;

import lombok.experimental.UtilityClass;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

//по идее это все сразу можно в тесты,
// но эта идея показалась мне не особо интуитивно понятной для проверяющего,
// поэтому часть кода напишу тут, хотя возможно это будет еще более контринтуитивно)
@UtilityClass
public final class HelloWorldSpeakerGenerator {
    public static DynamicType.Unloaded getUnloadedClass() {

        return new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.isToString())
            //я передумав, халоворд не буде
            .intercept(FixedValue.value("Welcome to the club, buddy!"))
            .make();
    }
}
