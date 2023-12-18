package edu.hw11.task3;

import java.lang.reflect.Modifier;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;

public final class FibonacciGenerator {
    private static final String GENERATED_CLASS_NAME = "DynamicallyGeneratedFibCounter";
    private static final String GENERATED_METHOD_NAME = "count";
    private static final String DESCRIPTOR = "(I)J";

    private FibonacciGenerator() {
    }

    @SuppressWarnings("checkstyle:AnonInnerLength")
    public static Class<?> generateFibonacciClass() {
        return new ByteBuddy()
            .subclass(Object.class)
            .name(GENERATED_CLASS_NAME)
            .defineMethod(GENERATED_METHOD_NAME, long.class, Modifier.PUBLIC)
            .withParameters(int.class)
            .intercept(
                new Implementation() {
                    //куда я попал...
                    //кстати, вопрос, пригождались подобнве штуки на работе?
                    @SuppressWarnings({"checkstyle:LambdaBodyLength", "checkstyle:MagicNumber"})
                    @Override
                    public @NotNull ByteCodeAppender appender(@NotNull Target implementationTarget) {
                        return (methodVisitor, context, methodDescription) -> {
                            Label l1 = new Label();

                            methodVisitor.visitCode();
                            // if (n < 2)
                            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
                            methodVisitor.visitInsn(Opcodes.ICONST_2);
                            methodVisitor.visitJumpInsn(Opcodes.IF_ICMPGE, l1);

                            //  return n;
                            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
                            methodVisitor.visitInsn(Opcodes.I2L);
                            methodVisitor.visitInsn(Opcodes.LRETURN);

                            //return count(n - 1) + count(n - 2);
                            methodVisitor.visitLabel(l1);
                            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
                            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
                            methodVisitor.visitInsn(Opcodes.ICONST_1);
                            methodVisitor.visitInsn(Opcodes.ISUB);
                            methodVisitor.visitMethodInsn(
                                Opcodes.INVOKEVIRTUAL,
                                GENERATED_CLASS_NAME,
                                GENERATED_METHOD_NAME,
                                DESCRIPTOR
                            );
                            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
                            methodVisitor.visitVarInsn(Opcodes.ILOAD, 1);
                            methodVisitor.visitInsn(Opcodes.ICONST_2);
                            methodVisitor.visitInsn(Opcodes.ISUB);
                            methodVisitor.visitMethodInsn(
                                Opcodes.INVOKEVIRTUAL,
                                GENERATED_CLASS_NAME,
                                GENERATED_METHOD_NAME,
                                DESCRIPTOR
                            );
                            methodVisitor.visitInsn(Opcodes.LADD);
                            methodVisitor.visitInsn(Opcodes.LRETURN);
                            methodVisitor.visitEnd();
                            return new ByteCodeAppender.Size(5, 2);
                        };
                    }

                    @Override
                    public @NotNull InstrumentedType prepare(@NotNull InstrumentedType instrumentedType) {
                        return instrumentedType;
                    }
                }
            )
            .make()
            .load(FibonacciGenerator.class.getClassLoader())
            .getLoaded();
    }
}
